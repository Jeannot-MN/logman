package com.jmn.logman.service.util.decision.out;

import com.jmn.logman.service.util.decision.DecisionLevel;

public class MinimumDecisionLevelPrettyPrinter extends DecisionTreePrettyPrinter {

    private final DecisionLevel minDecisionLevel;

    public MinimumDecisionLevelPrettyPrinter(DecisionLevel decisionLevel) {
        assert decisionLevel != null;
        this.minDecisionLevel = decisionLevel;
    }

    @Override
    public void outputDecisionLine(String line, DecisionLevel decisionLevel, int level) {
        if (minDecisionLevel.ordinal() <= decisionLevel.ordinal()) {
            super.outputDecisionLine(line, decisionLevel, level);
        }
    }
}
