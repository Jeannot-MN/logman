package com.jmn.logman.service.util.decision;

import java.io.Serializable;
import java.util.Objects;

public class Decision implements Serializable {

    private static final long serialVersionUID = 5466796206596767801L;

    private final String decisionDescription;
    private final DecisionLevel decisionLevel;

    public Decision(String decisionDescription, DecisionLevel decisionLevel) {
        this.decisionDescription = decisionDescription;
        this.decisionLevel = decisionLevel;
    }

    public String getDecision() {
        return decisionDescription;
    }

    public DecisionLevel getDecisionLevel() {
        return decisionLevel;
    }

    @Override
    public String toString() {
        return "Decision{" + "decision='" + decisionDescription + '\'' + ", decisionLevel=" + decisionLevel + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Decision decision1 = (Decision) o;
        return Objects.equals(decisionDescription, decision1.decisionDescription) && decisionLevel == decision1.decisionLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(decisionDescription, decisionLevel);
    }
}
