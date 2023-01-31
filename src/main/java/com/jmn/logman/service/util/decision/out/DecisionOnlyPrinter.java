package com.jmn.logman.service.util.decision.out;

import com.jmn.logman.service.util.decision.DecisionLevel;

import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SHORT_LINE;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SHORT_LINE_EMPTY;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SPACE;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.repeatString;

/**
 * {@link DecisionTreePrinter} to print only decisions made during a process.
 * <p>
 * e.g:
 * ===========================================================
 * ==Example (Total: 709ms)==
 * ===========================================================
 * ==  -- [INFO] Process for X: ******
 * ==  -- [INFO] Process ID: ******
 * ==  - Level 1:
 * ==    -- [INFO] Added ****
 * ==  - Level 1:
 * ==    -- [INFO] Adding: ***
 * ==    -- [INFO] Adding: ****
 * ==    -- [INFO] Adding: ****
 * ==    - Level 2:
 * ==      -- [INFO] Adding: ****
 * ==      -- [INFO] Adding: ****
 * ==      - Level 3:
 * ==        -- [INFO] Adding: ****
 * ==        -- [WARN] Removing: ****
 * ===========================================================
 */
public class DecisionOnlyPrinter extends AbstractDecisionPrinter {

    @Override
    public void outputChildHeader(String decisionTreeName, long timeMillis, double totalSeconds,
                                  int level) {
        if (decisionTreeName != null && level > 0) {
            getDecisionPrinterStringBuilder().append(SHORT_LINE + SPACE).append(repeatString(SHORT_LINE_EMPTY, level - 1)).append(" - ")
                    .append(decisionTreeName).append(":\n");
        }
    }

    @Override
    public void outputTimerLine(String timerName, long timeMillis, double totalSeconds, int level) {
        // Don't output timer lines, this is a decision only printer
    }

    @Override
    public void outputDecisionLine(String line, DecisionLevel decisionLevel, int level) {
        if (line != null) {
            getDecisionPrinterStringBuilder().append(SHORT_LINE + SPACE).append(repeatString(SHORT_LINE_EMPTY, level)).append(" -- ")
                    .append(String.format("[%s] %s", decisionLevel, line)).append("\n");
        }
    }
}
