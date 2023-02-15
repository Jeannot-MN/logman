package com.jmn.logman.service.bpm.task.user;

import com.jmn.logman.model.User;
import com.jmn.logman.model.exception.BusinessRuleViolationException;
import com.jmn.logman.service.bpm.common.CamundaVariable;
import com.jmn.logman.service.bpm.model.user.RegisterUserRequest;
import com.jmn.logman.service.bpm.task.AbstractApplicationBpmTask;
import com.jmn.logman.service.user.UserService;
import com.jmn.logman.service.util.security.PasswordGenerator;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SaveUserTask extends AbstractApplicationBpmTask {

    private static final Logger LOG = LoggerFactory.getLogger(SaveUserTask.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SaveUserTask(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void executeTask(DelegateExecution de) {
        RegisterUserRequest request = CamundaVariable.getRequest(de);

        User newUser = new User();

        if (StringUtils.trim(request.getFirstName()).equals("")) {
            throw new BusinessRuleViolationException("System cannot complete request. A user requires a name");
        }

        if (StringUtils.trim(request.getLastName()).equals("")) {
            throw new BusinessRuleViolationException("System cannot complete request. A user requires a last name");
        }

        newUser.setUsername(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setMobileNo(request.getMobileNo());
        newUser.setProfileImageUri(request.getProfileImageUri());
        if (StringUtils.isNotBlank(request.getFirstName()) && StringUtils.isNotBlank(request.getLastName())) {
            newUser.setDisplayName(request.getFirstName() + " " + request.getLastName());
        } else {
            newUser.setDisplayName(request.getEmail());
        }

        /*if (request.getSource() == RequestSource.ADMIN_SITE) {
            String plainTextPassword = generateRandomWord();
            request.setPlainTextPassword(plainTextPassword);
            LOG.info("New user password: {}", plainTextPassword);
            newUser.setPassword(passwordEncoder.encode(plainTextPassword));
        } else {
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }*/
        String plainTextPassword;
        if (StringUtils.trimToEmpty(request.getPlainTextPassword()).isEmpty()) {
            plainTextPassword = PasswordGenerator.generate();
            request.setPlainTextPassword(plainTextPassword);
            LOG.info("New user password: {}", plainTextPassword);
        } else {
            plainTextPassword = request.getPlainTextPassword();
        }
        newUser.setPassword(passwordEncoder.encode(plainTextPassword));

        LOG.info("Attempting to save user: {}", newUser);
        userService.save(newUser);

        CamundaVariable.REQUEST.setVariable(de, request);
        CamundaVariable.USER.setVariable(de, newUser);
    }
}
