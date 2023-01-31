package com.jmn.logman.service.util.decision.out;

public final class DecisionTreePrinterFactory {

    public static final String LONG_LINE = "===========================================================";
    public static final String SHORT_LINE = "==";
    public static final String SHORT_LINE_EMPTY = "    ";
    public static final String HALF_LINE_EMPTY = "  ";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";

    private DecisionTreePrinterFactory() {
        // prevent instantiation
    }

    public static String repeatString(String toRepeat, int numberRepeats) {
        if (toRepeat != null && numberRepeats > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < numberRepeats; i++) {
                sb.append(toRepeat);
            }
            return sb.toString();
        }
        return "";
    }

}
