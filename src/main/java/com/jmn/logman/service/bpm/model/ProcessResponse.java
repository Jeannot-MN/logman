package com.jmn.logman.service.bpm.model;


public abstract class ProcessResponse {

    private final String processInstanceId;

    public ProcessResponse(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }
}