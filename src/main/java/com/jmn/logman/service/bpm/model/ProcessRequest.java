package com.jmn.logman.service.bpm.model;

import com.jmn.logman.service.bpm.common.ProcessTypes;
import com.jmn.logman.service.security.jwt.ApplicationUserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ProcessRequest {

    private String processInstanceId;

    private Date dateCreated;

    private ApplicationUserDetails requestingUser;

    private List<String> candidateGroups = new ArrayList<>();

    private String lastAction;

    private String comment;

    public abstract ProcessTypes getType();

    public abstract String getBusinessKey();

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ApplicationUserDetails getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(ApplicationUserDetails requestingUser) {
        this.requestingUser = requestingUser;
    }

    public List<String> getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(List<String> candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ProcessRequest{" +
                "businessKey='" + getBusinessKey() + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", dateCreated=" + dateCreated +
                ", requestingUser=" + requestingUser +
                ", candidateGroups=" + candidateGroups +
                ", lastAction='" + lastAction + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
