package com.jmn.logman.service.bpm.model.user;

import com.jmn.logman.model.User;
import com.jmn.logman.service.bpm.model.ProcessResponse;

public class RegisterUserResponse extends ProcessResponse {

    private User user;

    public RegisterUserResponse(String processInstanceId, User user) {
        super(processInstanceId);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
