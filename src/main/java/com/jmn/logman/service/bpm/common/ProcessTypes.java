package com.jmn.logman.service.bpm.common;

public enum ProcessTypes {

    USER_REGISTRATION("UserRegistration");


    private final String id;

    ProcessTypes(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
