package com.jmn.logman.service.bpm.common;

import com.jmn.logman.model.User;
import com.jmn.logman.service.bpm.model.ProcessRequest;
import com.jmn.logman.service.bpm.model.ProcessResponse;
import com.jmn.logman.service.util.decision.DecisionLevel;
import com.jmn.logman.service.util.decision.DecisionTreeStopWatch;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public enum CamundaVariable {

    REQUEST(ProcessRequest.class),
    PROCESS_RESPONSE(ProcessResponse.class),
    PROCESS_OUTCOMES(ProcessOutcomes.class),
    PROCESS_DECISION_TREE(DecisionTreeStopWatch.class),
    USER(User.class),
    TARGET_USER(User.class),
    ERROR_MESSAGE(String.class);

    private static final Logger LOG = LoggerFactory.getLogger(CamundaVariable.class);

    private final Class<?> type;

    CamundaVariable(Class<?> type) {
        this.type = type;
    }

    /**
     * This expects that the value be set, if you want to sometimes support that it may be null, then
     * rather use this with a defaultValue below.
     */
    public <T> T getVariable(DelegateExecution de) {
        T ret = getVariable(de, null);
        Assert.notNull(ret, "BPM variable '" + name() + "' is expected to be populated.");
        validateType(ret);
        return ret;
    }

    @SuppressWarnings("unchecked")
    public <T> T getVariable(DelegateExecution de, T defaultValue) {
        Object ret = de.getVariable(name());
        if (ret == null) {
            return defaultValue;
        }
        validateType(ret);
        return (T) ret;
    }

    public Boolean hasVariable(DelegateExecution de) {
        return getVariable(de, null) != null;
    }

    public void setVariable(DelegateExecution de, Object value) {
        validateType(value);
        de.setVariable(name(), value);
    }

    public static void setVariable(DelegateExecution de, String name, Object value) {
        de.setVariable(name, value);
    }

    private <T> void validateType(Object ret) {
        if (ret != null) {
            assertType(ret, Serializable.class);
            assertType(ret, type);
        }
    }

    private void assertType(Object ret, Class<?> expectedType) {
        if (!expectedType.isInstance(ret)) {
            Assert.isInstanceOf(expectedType, ret, format("Got BPM variable (%s) of the wrong type %s expected %s", name(),
                    ret.getClass().getName(), expectedType.getName()));
        }
    }


    public Class<?> getType() {
        return type;
    }

    //=== helpers for commonly used variables ===//
    public static <W extends ProcessRequest> W getRequest(DelegateExecution de) {
        return REQUEST.getVariable(de);
    }


    public static <W extends ProcessResponse> W getResponse(DelegateExecution de) {
        return PROCESS_RESPONSE.getVariable(de);
    }

    public static DecisionTreeStopWatch getProcessDecisionTree(DelegateExecution de) {
        return PROCESS_DECISION_TREE.getVariable(de);
    }

    public static User getUser(DelegateExecution de) {
        return USER.getVariable(de);
    }

    public static User getTargetUser(DelegateExecution de) {
        return TARGET_USER.getVariable(de);
    }

    public static String getErrorMessage(DelegateExecution de) {
        return ERROR_MESSAGE.getVariable(de, null);
    }

    public static ProcessOutcomes getProcessOutcomes(DelegateExecution de) {
        ProcessOutcomes processOutcomes = PROCESS_OUTCOMES.getVariable(de, null);
        if (processOutcomes == null) {
            processOutcomes = new ProcessOutcomes();
        }
        return processOutcomes;
    }

    public static void addProcessOutcome(DelegateExecution de, String message) {
        addProcessOutcome(de, ProcessOutcomeType.INFORMATION, new ProcessOutcomeDetail(ProcessOutcomeType.INFORMATION, message));
    }

    public static void addProcessOutcome(DelegateExecution de, ProcessOutcomeType processOutcomeType, String message) {
        addProcessOutcome(de, processOutcomeType, new ProcessOutcomeDetail(processOutcomeType, message));
    }

    /**
     * It logs it for you too...
     * One can add multiple outcomes of the same type for multiple detail items..
     */
    public static void addProcessOutcome(DelegateExecution de, ProcessOutcomeType processOutcomeType
            , ProcessOutcomeDetail... details) {
        addProcessOutcome(de, processOutcomeType, asList(details));
    }

    public static void addProcessOutcome(DelegateExecution de, ProcessOutcomeType processOutcomeType
            , Collection<ProcessOutcomeDetail> details) {
        DecisionTreeStopWatch decisionTreeStopWatch = getProcessDecisionTree(de);
        DecisionLevel decisionLevel = DecisionLevel.INFO;
        switch (processOutcomeType.getSeverity()) {
            case WARNING:
                decisionLevel = DecisionLevel.WARN;
                break;
            case ERROR:
                decisionLevel = DecisionLevel.ERROR;
                break;
        }
        decisionTreeStopWatch.addDecisionToCurrent(format("%s (%s)", processOutcomeType.getDescription(), processOutcomeType), decisionLevel);
        for (ProcessOutcomeDetail detail : details) {
            String msg = "Setting process outcome: " + processOutcomeType + (detail != null ? " " + detail : "");
            switch (processOutcomeType.getSeverity()) {
                case INFO:
                    // Do we really need to log this?
                    // LOG.info(msg);
                    break;
                case WARNING:
                case ERROR:
                    // log process level errors eg. validation errors as warnings as they do not necessarily indicate
                    // system level errors.
                    LOG.warn(msg);
                    break;
            }
            if (detail != null) {
                decisionTreeStopWatch.addDecisionToCurrent(format("    %s (%s)", detail.getMessage(), detail.getKey()), decisionLevel);
            }
        }
        ProcessOutcomes processOutcomes = getProcessOutcomes(de);
        processOutcomes.add(details);
        PROCESS_OUTCOMES.setVariable(de, processOutcomes);
        PROCESS_DECISION_TREE.setVariable(de, decisionTreeStopWatch);
    }
}
