package com.jmn.logman.service.util.decision.out;

import com.jmn.logman.service.util.decision.DecisionLevel;

import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SHORT_LINE;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SHORT_LINE_EMPTY;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.repeatString;

/**
 * {@link DecisionTreePrinter} to print all decisions made during a process,
 * and all stopwatch timers recorded.
 * <p>
 * e.g:
 * ===========================================================
 * ==Example (Total: 709ms)==
 * ===========================================================
 * ==  -- 103ms  15% [INFO] Process for X: ******
 * ==  -- [INFO] Process ID: ******
 * ==  - 100ms  14%  Level 1:
 * ==    -- 100ms  14%  [INFO] Added ****
 * ==  - 100ms  14%  Level 1:
 * ==    -- 100ms  14%  [INFO] Adding: ***
 * ==    -- 100ms  14%  [INFO] Adding: ****
 * ==    -- 100ms  14%  [INFO] Adding: ****
 */
public class DecisionTreePrettyPrinter extends AbstractDecisionPrinter {

    @Override
    public void outputChildHeader(String decisionTreeName, long timeMillis, double totalTimeSeconds, int level) {
        if (decisionTreeName != null && level > 0) {
            getDecisionPrinterStringBuilder().append(SHORT_LINE + SHORT_LINE_EMPTY).append(repeatString(SHORT_LINE_EMPTY, level - 1)).append("- ")
                    .append(generateTimestamp(decisionTreeName, timeMillis, totalTimeSeconds)).append("\n");
        }
    }

    @Override
    public void outputTimerLine(String timerName, long timeMillis, double totalSeconds, int level) {
        if (timerName != null) {
            getDecisionPrinterStringBuilder().append(SHORT_LINE + SHORT_LINE_EMPTY).append(repeatString(SHORT_LINE_EMPTY, level)).append("- ")
                    .append(generateTimestamp(timerName, timeMillis, totalSeconds)).append("\n");
        }
    }

    @Override
    public void outputDecisionLine(String line, DecisionLevel decisionLevel, int level) {
        if (line != null) {
            getDecisionPrinterStringBuilder().append(SHORT_LINE + SHORT_LINE_EMPTY).append(repeatString(SHORT_LINE_EMPTY, level + 1)).append("-- ")
                    .append(String.format("[%s] %s", decisionLevel, line)).append("\n");
        }
    }

}
