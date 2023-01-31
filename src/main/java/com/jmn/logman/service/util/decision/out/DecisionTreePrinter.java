package com.jmn.logman.service.util.decision.out;

import com.jmn.logman.service.util.decision.DecisionLevel;

/**
 * Interface defining printers for the {@link com.jmn.logman.service.util.decision.DecisionTreeStopWatch}. Used to output all information from the
 * {@link com.jmn.logman.service.util.decision.DecisionTreeStopWatch} into the required format, implement with required implementation.
 * <p>
 * Used with the {@link com.jmn.logman.service.util.decision.DecisionTreeStopWatch#output(DecisionTreePrinter)} method.
 * <p>
 * e.g. {@link DecisionTreePrettyPrinter},
 * {@link DecisionOnlyPrinter} and
 * {@link StopWatchOnlyPrinter}
 */
public interface DecisionTreePrinter {

    /**
     * Called before print, with the title of the decision tree node, and the total running time of the node
     * and its children in milliseconds.
     *
     * @param decisionTreeName The name of the current decision tree node.
     * @param totalTimeMillis  The current running time in milliseconds.
     */
    void outputBefore(String decisionTreeName, long totalTimeMillis);

    /**
     * Called after print, with the title of the tree node.
     *
     * @param decisionTreeName Name of the current tree node.
     */
    void outputAfter(String decisionTreeName);

    /**
     * Called to print for each child node in the tree, called with the name of the node, the time the node has been running,
     * the total time that the parent has been running and the level in the tree that the node is in, relative to the start node.
     *
     * @param decisionTreeName The name of the child node.
     * @param timeMillis       The time in milliseconds that the node has been running.
     * @param totalSeconds     The total time in seconds that the parent node has been running.
     * @param level            The level of the current node, relative to the starting node.
     */
    void outputChildHeader(String decisionTreeName, long timeMillis, double totalSeconds, int level);

    /**
     * Called to output each timer line, for each stopwatch time that has been started on tree stopwatch, see:
     * {@link com.jmn.logman.service.util.time.TreeStopWatch#start(String)} and
     * {@link com.jmn.logman.service.util.time.TreeStopWatch#stop()}
     *
     * @param timerName    The name of the stopwatch timer under the node.
     * @param timeMillis   The total time in milliseconds that the current timer has been running.
     * @param totalSeconds The total time in seconds that the parent node has been running.
     * @param level        The level of the {@link com.jmn.logman.service.util.time.TreeStopWatch} node.
     */
    void outputTimerLine(String timerName, long timeMillis, double totalSeconds, int level);

    /**
     * Called to output each decision line under a timer line.
     *
     * @param line          The decision line that was added.
     * @param decisionLevel The level of the decision that is made, e.g. {@link DecisionLevel#INFO}
     * @param level         The level of the current {@link com.jmn.logman.service.util.time.TreeStopWatch} node.
     */
    void outputDecisionLine(String line, DecisionLevel decisionLevel, int level);

}
