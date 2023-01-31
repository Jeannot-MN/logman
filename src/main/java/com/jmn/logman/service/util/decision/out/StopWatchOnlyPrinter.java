package com.jmn.logman.service.util.decision.out;


import com.jmn.logman.service.util.decision.DecisionLevel;

import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SHORT_LINE;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SHORT_LINE_EMPTY;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.repeatString;

/**
 * {@link DecisionTreePrinter} to print all stopwatch timers recorded during a process.
 * <p>
 * e.g:
 * ===========================================================
 * ==Example Customise Benefit Authorisation (Total: 709ms)==
 * ===========================================================
 * ==  - 103ms  15%  Level 1:
 * ==  - 100ms  14%  Level 1:
 * ==    - 100ms  100%  Level 2:
 * ==  - 506ms  71%  Level 1:
 * ==    - 132ms  26%  Level 2:
 * ==    - 140ms  28%  Level 2:
 * ==    - 234ms  46%  Level 2:
 * ==      - 101ms  43%  Level 3:
 * ==      - 133ms  57%  Level 3:
 * ==        - 133ms  100%  Level 4:
 * ===========================================================
 */
public class StopWatchOnlyPrinter extends AbstractDecisionPrinter {

    @Override
    public void outputChildHeader(String decisionTreeName, long timeMillis, double totalSeconds, int level) {
        if (decisionTreeName != null && level > 0) {
            getDecisionPrinterStringBuilder().append(SHORT_LINE + " ").append(repeatString(SHORT_LINE_EMPTY, level - 1)).append(" - ")
                    .append(generateTimestamp(decisionTreeName, timeMillis, totalSeconds)).append(":\n");
        }
    }

    @Override
    public void outputTimerLine(String timerName, long timeMillis, double totalSeconds, int level) {
        if (timerName != null) {
            getDecisionPrinterStringBuilder().append(SHORT_LINE + " ").append(repeatString(SHORT_LINE_EMPTY, level)).append(" - ")
                    .append(generateTimestamp(timerName, timeMillis, totalSeconds)).append(":\n");
        }
    }

    @Override
    public void outputDecisionLine(String line, DecisionLevel decisionLevel, int level) {
        // Don't write decision lines for timer only printer.
    }
}
