package com.jmn.logman.service.util.time;

import org.springframework.lang.NonNull;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class TreeStopWatchBase<T extends TreeStopWatchBase> extends StopWatch {

    private final String name;
    private String currentTask;

    private T parentStopWatch;
    private Map<String, T> childStopWatches = new HashMap<>();

    public TreeStopWatchBase(String id) {
        super(id);
        this.name = id;
    }

    @Override
    public void start(@NonNull String taskName) throws IllegalStateException {
        super.start(taskName);
        this.currentTask = taskName;
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        this.currentTask = null;
    }

    /**
     * Utility method to stop all running stopwatches below the current node.
     */
    public void stopAllRunning() {
        for (T stopWatch : childStopWatches.values()) {
            if (stopWatch != null) {
                stopWatch.stopAllRunning();
            }
        }
        if (isRunning()) {
            stop();
        }
    }

    @Override
    public void setKeepTaskList(boolean keepTaskList) {
        super.setKeepTaskList(true);
    }

    @SuppressWarnings("unchecked")
    public T getHighestParent() {
        T stopWatch = this.parentStopWatch;
        if (stopWatch != null) {
            return (T) stopWatch.getHighestParent();
        }
        return (T) this;
    }

    @Override
    public String getId() {
        return name;
    }

    public String getCurrentTask() {
        return currentTask;
    }

    protected void setParentStopWatch(T parentStopWatch) {
        this.parentStopWatch = parentStopWatch;
    }

    @Override
    public String toString() {
        return "TreeStopWatchBase{" + "name='" + name + '\'' + ", currentTask='" + currentTask + '\''
                + ", childStopWatches=" + childStopWatches + "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeStopWatchBase<?> that = (TreeStopWatchBase<?>) o;
        return Objects.equals(name, that.name) && Objects.equals(currentTask, that.currentTask)
                && Objects.equals(childStopWatches, that.childStopWatches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currentTask, childStopWatches);
    }

    protected T getParentStopWatch() {
        return parentStopWatch;
    }

    protected Map<String, T> getChildStopWatches() {
        return childStopWatches;
    }
}
