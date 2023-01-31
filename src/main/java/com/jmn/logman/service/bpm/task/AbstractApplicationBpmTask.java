package com.jmn.logman.service.bpm.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractApplicationBpmTask implements JavaDelegate {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public final void execute(DelegateExecution de) throws Exception {
        try {
            if (LOG.isTraceEnabled()) LOG.trace("Starting.");
            executeTask(de);

            if (LOG.isTraceEnabled()) LOG.trace("Completed.");

        } catch (Exception e) {
            throw e;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    protected abstract void executeTask(DelegateExecution de) throws Throwable;
}
