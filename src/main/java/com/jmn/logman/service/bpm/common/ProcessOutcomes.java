package com.jmn.logman.service.bpm.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ProcessOutcomes implements Serializable {

    private static final long serialVersionUID = 2457782292608203716L;

    private final List<ProcessOutcomeDetail> outcomes = new ArrayList<>();

    public void add(Collection<ProcessOutcomeDetail> newDetails) {
        outcomes.addAll(newDetails);
    }

    public void add(ProcessOutcomeDetail... newDetails) {
        this.outcomes.addAll(Arrays.asList(newDetails));
    }

    public int size() {
        return outcomes.size();
    }

    @JsonIgnore
    public List<ProcessOutcomeDetail> getOutcomes() {
        return outcomes;
    }

    public boolean containsAny(String... processOutcomeTypeNames) {
        for (String typeName : processOutcomeTypeNames) {
            if (outcomes.stream().anyMatch(e -> e.getKey().equals(ProcessOutcomeType.valueOf(typeName)))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProcessOutcomes that = (ProcessOutcomes) o;
        return Objects.equals(outcomes, that.outcomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outcomes);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (ProcessOutcomeDetail e : outcomes) {
            ret.append(e.getKey());
            ret.append(" : ");
            ret.append(e.getMessage());
            ret.append("\n");
        }
        return StringUtils.join(ret, "\n");
    }
}
