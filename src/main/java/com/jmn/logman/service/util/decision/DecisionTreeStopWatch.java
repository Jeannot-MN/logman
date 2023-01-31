package com.jmn.logman.service.util.decision;

import com.jmn.logman.service.util.decision.out.DecisionOnlyPrinter;
import com.jmn.logman.service.util.decision.out.DecisionTreePrettyPrinter;
import com.jmn.logman.service.util.decision.out.DecisionTreePrinter;
import com.jmn.logman.service.util.decision.out.StopWatchOnlyPrinter;
import com.jmn.logman.service.util.time.TreeStopWatchBase;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An extension of the {@link TreeStopWatchBase} class.
 * The DecisionTreeStopWatch also can keep track of decisions made during the process being timed, allowing for
 * a tree decision log, with stopwatch times for each level.
 * <p>
 * The first {@link DecisionTreeStopWatch} created serves as the root node in the tree, adding children everytime the method
 * {@link #addDecisionToCurrent(String, DecisionLevel)} method is called with a UNIQUE identifier.
 * <p>
 * e.g.
 * RootTree --+-- ChildOne --+-- SubChildOne
 * |              +-- SubChildThree
 * +-- ChildTwo
 */
public class DecisionTreeStopWatch extends TreeStopWatchBase<DecisionTreeStopWatch> {

    private final static double MILLISECONDS_IN_SECONDS = 1000.0;

    private final Map<String, List<Decision>> stopwatchDecisionMap = new HashMap<>();
    private final List<String> childrenOrdering = new ArrayList<>();
    private long childrenTime;

    public DecisionTreeStopWatch(String id) {
        super(id);
    }

    /**
     * Add a new {@link DecisionTreeStopWatch} as a child to the current node in the tree.
     * First searches the tree below this node for the entered ID,
     * if the entered ID is found this method instead returns the existing DecisionTreeStopWatch.
     *
     * @param id The unique identifier for a new node in the tree.
     * @return The created {@link DecisionTreeStopWatch}, or the existing {@link DecisionTreeStopWatch} if found.
     */
    public DecisionTreeStopWatch addChildDecisionStopWatch(String id) {
        DecisionTreeStopWatch decisionTreeStopWatch = getStopWatch(id);

        if (decisionTreeStopWatch == null) {
            decisionTreeStopWatch = new DecisionTreeStopWatch(id);
            decisionTreeStopWatch.setParentStopWatch(this);
            getChildStopWatches().put(id, decisionTreeStopWatch);
        }

        addChildToOrdering(id);
        return decisionTreeStopWatch;
    }

    /**
     * Does a search through the entire tree below this node for the input id (recursive search).
     * The first found {@link DecisionTreeStopWatch} with matching identifier is returned, else returns NULL.
     *
     * @param id The unique identifier to search for.
     * @return The found {@link DecisionTreeStopWatch} or null if not found.
     */
    public DecisionTreeStopWatch getStopWatch(String id) {
        return getChildOrSelf(id);
    }

    private DecisionTreeStopWatch getChildOrSelf(String id) {
        if (Objects.equals(getId(), id)) {
            return this;
        }
        if (getChildStopWatches().containsKey(id)) {
            return getChildStopWatches().get(id);
        }
        for (DecisionTreeStopWatch decisionTreeStopWatch : getChildStopWatches().values()) {
            DecisionTreeStopWatch foundChild = decisionTreeStopWatch.getChildOrSelf(id);
            if (foundChild != null) {
                return foundChild;
            }
        }
        return null;
    }

    /**
     * Add a decision to the currently running stop watch, throws an {@link IllegalStateException} if there is no currently
     * running stopwatch task.
     *
     * @param decision      The string description of the decision made (similar to a log line).
     * @param decisionLevel The level of the decision made, supports: {@link DecisionLevel#INFO}, {@link DecisionLevel#TRACE},
     *                      {@link DecisionLevel#WARN}, {@link DecisionLevel#ERROR}
     * @throws IllegalStateException Thrown if there is no running stopwatch in the current node.
     */
    public void addDecisionToCurrent(String decision, DecisionLevel decisionLevel)
            throws IllegalStateException {
        if (!isRunning()) {
            throw new IllegalStateException("Can't add decision to StopWatch: no stopwatch is running");
        }
        String currentTask = currentTaskName();
        if (!stopwatchDecisionMap.containsKey(currentTask)) {
            stopwatchDecisionMap.put(currentTask, new ArrayList<>());
        }
        stopwatchDecisionMap.get(currentTask).add(new Decision(decision, decisionLevel));
    }

    /**
     * Add an {@link DecisionLevel#INFO} level decision to the current timer, throwing {@link IllegalStateException} if no timer is currently running.
     *
     * @param decision The string description of the decision made.
     * @throws IllegalStateException Thrown if no timer is currently running.
     */
    public void infoDecision(String decision) throws IllegalStateException {
        addDecisionToCurrent(decision, DecisionLevel.INFO);
    }

    /**
     * Add an {@link DecisionLevel#ERROR} level decision to the current timer, throwing {@link IllegalStateException} if no timer is currently running.
     *
     * @param decision The string description of the decision made.
     * @throws IllegalStateException Thrown if no timer is currently running.
     */
    public void errorDecision(String decision) throws IllegalStateException {
        addDecisionToCurrent(decision, DecisionLevel.ERROR);
    }

    /**
     * Add an {@link DecisionLevel#WARN} level decision to the current timer, throwing {@link IllegalStateException} if no timer is currently running.
     *
     * @param decision The string description of the decision made.
     * @throws IllegalStateException Thrown if no timer is currently running.
     */
    public void warnDecision(String decision) throws IllegalStateException {
        addDecisionToCurrent(decision, DecisionLevel.WARN);
    }

    /**
     * Add an {@link DecisionLevel#TRACE} level decision to the current timer, throwing {@link IllegalStateException} if no timer is currently running.
     *
     * @param decision The string description of the decision made.
     * @throws IllegalStateException Thrown if no timer is currently running.
     */
    public void traceDecision(String decision) throws IllegalStateException {
        addDecisionToCurrent(decision, DecisionLevel.TRACE);
    }

    /**
     * Add an {@link DecisionLevel#TRACE} level decision to the current timer, throwing {@link IllegalStateException} if no timer is currently running.
     *
     * @param decision The string description of the decision made.
     * @throws IllegalStateException Thrown if no timer is currently running.
     */
    public void fatalErrorDecision(String decision) throws IllegalStateException {
        addDecisionToCurrent(decision, DecisionLevel.FATAL);
    }

    /**
     * Get the current list of decisions, stored in the form of a map of stopwatch identifier to a list of decisions,
     * multiple decisions can be made during the run of a single stop watch.
     *
     * @return Unmodifiable Map of stopwatch identifiers to decisions made.
     */
    public Map<String, List<Decision>> getStopwatchDecisionMap() {
        return Collections.unmodifiableMap(stopwatchDecisionMap);
    }

    private void addTimeToParents(long timeMillis) {
        if (!isRunning()) {
            childrenTime += timeMillis;
            if (getParentStopWatch() != null) {
                getParentStopWatch().addTimeToParents(timeMillis);
            }
        }
    }

    /**
     * Do a pretty print of the current context of the {@link DecisionTreeStopWatch}, formatted and indented according to
     * the level of the node in the tree, prints from the node where it is called downwards.
     *
     * @return The pretty printed version of the current {@link DecisionTreeStopWatch} context.
     */
    @Override
    public String prettyPrint() {
        DecisionTreePrinter out = new DecisionTreePrettyPrinter();
        output(out);
        return out.toString();
    }

    /**
     * Similar function to {@link #prettyPrint()} except this only prints the decisions made during the process,
     * ignoring timestamps and stopwatches.
     *
     * @return The decisions only printout of the current {@link DecisionTreeStopWatch} context.
     */
    public String printDecisions() {
        DecisionOnlyPrinter out = new DecisionOnlyPrinter();
        output(out);
        return out.toString();
    }

    /**
     * Similar function to {@link #prettyPrint()} except this only prints the stopwatch timings during the process,
     * ignoring decisions.
     *
     * @return The timing only printout of the current {@link DecisionTreeStopWatch} context.
     */
    public String printTimings() {
        StopWatchOnlyPrinter out = new StopWatchOnlyPrinter();
        output(out);
        return out.toString();
    }

    /**
     * Start a new  stopwatch timer, adding a decision line to it at the same time. First creates a new StopWatch using the
     * {@link TreeStopWatchBase#start(String)} method, then attaches a decision to the createded stopwatch.
     *
     * @param taskName      The name of the task being created, e.g. babiesFirstTimer
     * @param decision      The decision made during the process when this timer is added
     * @param decisionLevel The type of decision made.
     * @throws IllegalStateException Thrown if there is already a stopwatch running on the current node.
     */
    public void start(String taskName, String decision, DecisionLevel decisionLevel)
            throws IllegalStateException {
        start(taskName);
        addDecisionToCurrent(decision, decisionLevel);
    }

    @Override
    public void start(@NonNull String taskName) throws IllegalStateException {
        super.start(taskName);
        addChildToOrdering(taskName);
    }

    /**
     * Stop the current running stopwatch, updating the current runtime of all parents.
     *
     * @throws IllegalStateException Thrown if there is no stopwatch currently running.
     */
    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        addTimeToParents(getLastTaskTimeMillis());
    }

    private void addChildToOrdering(String lastTaskName) {
        if (lastTaskName != null && !childrenOrdering.contains(lastTaskName)) {
            childrenOrdering.add(lastTaskName);
        }
    }

    /**
     * Output the current {@link DecisionTreeStopWatch} context to the input {@link DecisionTreePrinter}.
     * Calls {@link DecisionTreePrinter} in the following way:
     * * {@link DecisionTreePrinter#outputBefore(String, long)} called before full print, with the title of the current node and total run time.
     * * {@link DecisionTreePrinter#outputChildHeader(String, long, double, int)} called to print a child node title, called once per each child node.
     * * {@link DecisionTreePrinter#outputTimerLine(String, long, double, int)} called to print each timer line, for each stopwatch started and stopped.
     * * {@link DecisionTreePrinter#outputDecisionLine(String, DecisionLevel, int)} called to print each decision line under a timer line.
     * * {@link DecisionTreePrinter#outputAfter(String)} called after the output is complete.
     *
     * @param decisionTreePrinter The decision tree outputer to use, e.g. {@link DecisionTreePrettyPrinter} or {@link DecisionOnlyPrinter}.
     */
    public void output(DecisionTreePrinter decisionTreePrinter) {
        decisionTreePrinter.outputBefore(this.getId(), getTotalTimeMillis());
        output(decisionTreePrinter, 0);
        decisionTreePrinter.outputAfter(this.getId());
    }

    private void output(DecisionTreePrinter decisionTreePrinter, int level) {

        decisionTreePrinter.outputChildHeader(this.getId(), childrenTime, getParentTotalTimeSeconds(), level);

        for (String id : childrenOrdering) {
            TaskInfo taskInfo = getTask(id);
            DecisionTreeStopWatch childWatch = getChild(id);

            if (childWatch != null) {
                childWatch.output(decisionTreePrinter, level + 1);
            } else if (taskInfo != null) {
                decisionTreePrinter.outputTimerLine(taskInfo.getTaskName(), taskInfo.getTimeMillis(),
                        getTotalTimeSeconds(), level);

                List<Decision> decisions = stopwatchDecisionMap.get(taskInfo.getTaskName());
                outputDecisionLine(decisionTreePrinter, level, decisions);
            }
        }
    }

    private void outputDecisionLine(DecisionTreePrinter decisionTreePrinter, int level,
                                    List<Decision> decisions) {
        if (decisions != null) {
            for (Decision decision : decisions) {
                decisionTreePrinter.outputDecisionLine(decision.getDecision(), decision.getDecisionLevel(),
                        level);
            }
        }
    }

    private TaskInfo getTask(String name) {
        for (TaskInfo taskInfo : getTaskInfo()) {
            if (Objects.equals(name, taskInfo.getTaskName())) {
                return taskInfo;
            }
        }
        return null;
    }

    private DecisionTreeStopWatch getChild(String name) {
        if (name != null) {
            return getChildStopWatches().get(name);
        }
        return null;
    }

    /**
     * The total running time of this node, including all sub node in the tree.
     * Value is updated when {@link DecisionTreeStopWatch#stop()} methods are called.
     *
     * @return The current run time of the current node and all children.
     */
    @Override
    public long getTotalTimeMillis() {
        return childrenTime;
    }

    /**
     * The total running time of this node in seconds, including all sub node in the tree.
     * Value is updated when {@link DecisionTreeStopWatch#stop()} methods are called.
     *
     * @return The current run time in seconds of the current node and all children.
     */
    @Override
    public double getTotalTimeSeconds() {
        return getTotalTimeMillis() / MILLISECONDS_IN_SECONDS;
    }

    /**
     * The total running time of the parent node above the current node, including all sub node in the tree.
     * Value is updated when {@link DecisionTreeStopWatch#stop()} methods are called.
     *
     * @return The current run time of the current node and all children,
     * returns the current nodes time if it is the top most parent.
     */
    public long getParentTotalTimeMillis() {
        if (getParentStopWatch() != null) {
            return getParentStopWatch().childrenTime;
        }
        return childrenTime;
    }

    /**
     * The total running time of the parent node above the current node in seconds, including all sub node in the tree.
     * Value is updated when {@link DecisionTreeStopWatch#stop()} methods are called.
     *
     * @return The current run time of the parent node above the current and all children (in seconds),
     * returns the current nodes time if it is the top most parent.
     */
    public double getParentTotalTimeSeconds() {
        return getParentTotalTimeMillis() / MILLISECONDS_IN_SECONDS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DecisionTreeStopWatch that = (DecisionTreeStopWatch) o;
        return childrenTime == that.childrenTime && Objects.equals(stopwatchDecisionMap, that.stopwatchDecisionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stopwatchDecisionMap, childrenTime);
    }

    @Override
    public String toString() {
        return "DecisionTreeStopWatch{" + "stopwatchDecisionMap=" + stopwatchDecisionMap + ", childrenTime="
                + childrenTime + "} " + super.toString();
    }
}
