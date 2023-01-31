package com.jmn.logman.service.util.decision.out;


import java.text.NumberFormat;

import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.HALF_LINE_EMPTY;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.LONG_LINE;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.NEW_LINE;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SHORT_LINE;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.SPACE;
import static com.jmn.logman.service.util.decision.out.DecisionTreePrinterFactory.repeatString;

public abstract class AbstractDecisionPrinter implements DecisionTreePrinter {

    private static final int MIN_DIGITS_PERCENT = 3;
    private static final int MIN_DIGITS_TIME_IN_MS = 5;

    private final StringBuilder decisionPrinterStringBuilder = new StringBuilder();

    @Override
    public void outputBefore(final String decisionTreeName, final long totalTimeMillis) {
        decisionPrinterStringBuilder.append(generateTitle(decisionTreeName, totalTimeMillis));
    }

    @Override
    public void outputAfter(final String decisionTreeName) {
        decisionPrinterStringBuilder.append(LONG_LINE).append(NEW_LINE);
    }

    @Override
    public String toString() {
        return decisionPrinterStringBuilder.toString();
    }

    String generateTimestamp(final String title, final long timeMillis, final double totalSeconds) {

        final NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(MIN_DIGITS_TIME_IN_MS);
        nf.setGroupingUsed(false);

        final NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(MIN_DIGITS_PERCENT);
        pf.setGroupingUsed(false);

        final double timeSeconds = timeMillis / 1000.0;
        final String timeMillisFormat = nf.format(timeMillis);
        final String percentage;

        if (timeSeconds == 0.0) {
            percentage = pf.format(0.0);
        } else {
            percentage = pf.format(timeSeconds / totalSeconds);
        }
        return timeMillisFormat + "ms" + HALF_LINE_EMPTY + percentage + HALF_LINE_EMPTY + title;
    }

    private String generateTitle(final String title, final long totalTimeMillis) {
        final String titleLine = title + " (Total: " + totalTimeMillis + "ms)";
        final int diff = LONG_LINE.length() - titleLine.length() - (SHORT_LINE.length() * 2);

        if (diff > 0) {
            final String spacing = repeatString(SPACE, diff / 2);
            return LONG_LINE + NEW_LINE + SHORT_LINE + spacing + titleLine + spacing + SHORT_LINE + NEW_LINE + LONG_LINE + NEW_LINE;
        } else {
            return LONG_LINE + NEW_LINE + SHORT_LINE + SPACE + titleLine + SPACE + SHORT_LINE + NEW_LINE + LONG_LINE + NEW_LINE;
        }
    }

    StringBuilder getDecisionPrinterStringBuilder() {
        return decisionPrinterStringBuilder;
    }
}
