package com.jmn.logman.service.bpm.common;


import java.io.Serializable;
import java.util.Objects;

public class ProcessOutcomeDetail implements Serializable {

    private static final long serialVersionUID = -1832342875661604287L;

    /**
     * The component generating the details should define it's own keys
     */
    private ProcessOutcomeType key;

    /**
     * A detailed message of the issue/info, like a useful exception message.
     */
    private String message;


    public ProcessOutcomeDetail() {
    }

    public ProcessOutcomeDetail(ProcessOutcomeType key, String message) {
        this.key = key;
        this.message = message;
    }

    public ProcessOutcomeType getKey() {
        return key;
    }

    public void setKey(ProcessOutcomeType key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcessOutcomeDetail that = (ProcessOutcomeDetail) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, message);
    }

    @Override
    public String toString() {
        return "ProcessOutcomeDetail{" +
                "key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}