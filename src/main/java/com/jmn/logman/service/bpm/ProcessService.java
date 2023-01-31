package com.jmn.logman.service.bpm;

import com.jmn.logman.service.bpm.model.user.RegisterUserRequest;
import com.jmn.logman.service.bpm.model.user.RegisterUserResponse;

public interface ProcessService {

    RegisterUserResponse registerUser(RegisterUserRequest request);
}
