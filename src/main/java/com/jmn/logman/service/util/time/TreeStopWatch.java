package com.jmn.logman.service.util.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Map;

/**
 * Displays StopWatches in hierarchical view. Start with a parent stopwatch calling the constructor.
 * To create a child stopwatch use createChildStopWatch on the top level parent. Children can be nested, ie
 * you can create a child and record some timings, and then create another child within a method call in the previous child.
 * This will display as
 * <p>
 * --Parent Timings--
 * --Child 1 Timings--
 * --Child 2 Timings--
 */
public class TreeStopWatch extends TreeStopWatchBase<TreeStopWatch> {

    private static Logger LOG = LoggerFactory.getLogger(TreeStopWatch.class);

    private static int MIN_DIGITS_PERCENT = 3;
    private static int MIN_DIGITS_TIME_IN_MS = 5;
    private static String ASTERIXIS = "***********************************************************";


    private LinkedList<String> stopWatchPath = new LinkedList<>();

    /**
     * Use constructor only to create uppermost parent timer.
     *
     * @param id the id of the timer
     */
    public TreeStopWatch(String id) {
        super(id);

        this.stopWatchPath.push(id);
    }

    /**
     * Must be called on highest node in Tree (the uppermost parent).
     * This method finds the last node in the tree and attached a new child to it.
     *
     * @param thisId The Id of the new Child StopWatch
     * @return the newly created stopwatch
     */
    public TreeStopWatch createChildStopWatch(String thisId) {
        if (CollectionUtils.isEmpty(stopWatchPath)) {
            return new TreeStopWatch(thisId);
        }

        String lastStopWatch = stopWatchPath.getFirst();

        if (this.getId().equals(lastStopWatch)) {
            TreeStopWatch newStopWatch = findExistingChildStopWatch(this, thisId);
            if (newStopWatch == null) {
                newStopWatch = new TreeStopWatch(thisId);
                getChildStopWatches().put(getCurrentTask(), newStopWatch);
                newStopWatch.setParentStopWatch(this);
            }

            return newStopWatch;
        }

        //Didnt find it? Check the children
        TreeStopWatch newStopWatch =
                createStopWatchWithParentTaskFromChildren(getChildStopWatches(), lastStopWatch, thisId);
        if (newStopWatch != null) {
            return newStopWatch;
        }

        LOG.error(String.format("Could not find StopWatch with id %s", lastStopWatch));
        return new TreeStopWatch(thisId);
    }

    @Override
    public void start(String taskName) {
        super.start(taskName);

        TreeStopWatch highestParent = getHighestParent();
        if (!highestParent.getStopWatchPath().contains(getId())) {
            //noinspection unchecked
            highestParent.getStopWatchPath().push(getId());
        }
    }

    @Override
    public void stop() {
        super.stop();

        TreeStopWatch highestParent = getHighestParent();
        highestParent.getStopWatchPath().pop();
    }

    @Override
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder("\n");
        String title = String.format("***TreeStopWatch for %s***", this.getId());
        sb.append("\n").append(ASTERIXIS).append("\n");
        sb.append(title).append("\n");
        sb.append(ASTERIXIS).append("\n");

        prettyPrint(sb, 0);
        sb.append(ASTERIXIS).append("\n");

        return sb.toString();
    }

    private void prettyPrint(StringBuilder sb, Integer iteration) {
        iteration++;

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(MIN_DIGITS_TIME_IN_MS);
        nf.setGroupingUsed(false);
        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(MIN_DIGITS_PERCENT);
        pf.setGroupingUsed(false);
        TaskInfo[] taskInfos = this.getTaskInfo();

        for (TaskInfo task : taskInfos) {
            String appendThis = determineHowManyTabs(iteration);

            sb.append(appendThis);
            sb.append(nf.format(task.getTimeMillis())).append("  ");
            sb.append(pf.format(task.getTimeSeconds() / this.getTotalTimeSeconds())).append("  ");
            sb.append(task.getTaskName()).append("\n");
            TreeStopWatch childStopWatch = getChildStopWatches().get(task.getTaskName());
            if (childStopWatch != null) {
                childStopWatch.prettyPrint(sb, iteration);
            }
        }
    }

    private String determineHowManyTabs(Integer iteration) {
        StringBuilder sb = new StringBuilder();
        sb.append("** ");
        for (int count = 1; count < iteration; count++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    private TreeStopWatch findExistingChildStopWatch(TreeStopWatch treeStopWatch, String thisId) {
        if (treeStopWatch.getChildStopWatches() != null) {
            for (TreeStopWatch childStopWatch : treeStopWatch.getChildStopWatches().values()) {
                if (childStopWatch.getId().equals(thisId)) {
                    return childStopWatch;
                }
            }
        }
        return null;
    }

    private TreeStopWatch createStopWatchWithParentTaskFromChildren(Map<String, TreeStopWatch> childStopWatches,
                                                                    String parentId, String thisId) {
        for (TreeStopWatch treeStopWatch : childStopWatches.values()) {
            if (treeStopWatch.getId().equals(parentId)) {
                TreeStopWatch newStopWatch = findExistingChildStopWatch(treeStopWatch, thisId);
                if (newStopWatch == null) {
                    newStopWatch = new TreeStopWatch(thisId);
                    treeStopWatch.getChildStopWatches().put(treeStopWatch.getCurrentTask(), newStopWatch);
                    newStopWatch.setParentStopWatch(treeStopWatch);
                }

                return newStopWatch;
            }
        }

        for (TreeStopWatch treeStopWatch : childStopWatches.values()) {
            TreeStopWatch returned =
                    createStopWatchWithParentTaskFromChildren(treeStopWatch.getChildStopWatches(), parentId, thisId);
            if (returned != null) {
                return returned;
            }
        }

        return null;
    }

    public LinkedList<String> getStopWatchPath() {
        return stopWatchPath;
    }

}
