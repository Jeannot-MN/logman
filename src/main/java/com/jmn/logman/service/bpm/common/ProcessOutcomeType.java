package com.jmn.logman.service.bpm.common;

/*
 * DEVNOTE: Pretty please don't add too many flags, only add things that affect a process flow.
 * Rather try to make more generic high level flag and further qualify the specific case with ProcessOutcomeDetail
 *
 * In general only have negative outcome types, unless multiple routes are supported.
 * Only have business level errors here that the frontend and down-streams need to act upon.
 * For system level errors rather throw an exception
 */
public enum ProcessOutcomeType {
    DECISION_REASON(ProcessOutcomeSeverity.INFO, "Reason for a decision"),
    INFORMATION(ProcessOutcomeSeverity.INFO, "General information"),
    ERROR(ProcessOutcomeSeverity.ERROR, "An exception"),
    ACTION_CREATE(ProcessOutcomeSeverity.INFO, "The action to create"),
    ACTION_UPDATE(ProcessOutcomeSeverity.INFO, "The action to update"),
    ACTION_REMOVE(ProcessOutcomeSeverity.INFO, "The action to remove"),
    ACTION_CANCEL(ProcessOutcomeSeverity.INFO, "The action to cancel");

    private final String description;
    private final ProcessOutcomeSeverity severity;

    ProcessOutcomeType(ProcessOutcomeSeverity severity, String description) {
        this.description = description;
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public ProcessOutcomeSeverity getSeverity() {
        return severity;
    }

    public enum ProcessOutcomeSeverity {
        /**
         * FYI
         */
        INFO,

        /**
         * possible user action required, but processing may continue
         */
        WARNING,

        /**
         * something bad happened, so exit early, i.e. don't persist!
         */
        ERROR
    }
}