package com.jmn.logman.service.bpm.task.user;

import com.jmn.logman.model.User;
import com.jmn.logman.model.exception.BusinessRuleViolationException;
import com.jmn.logman.service.bpm.common.CamundaVariable;
import com.jmn.logman.service.bpm.model.user.RegisterUserRequest;
import com.jmn.logman.service.bpm.task.AbstractApplicationBpmTask;
import com.jmn.logman.service.user.UserService;
import com.jmn.logman.service.util.security.PasswordDecision;
import com.jmn.logman.service.util.security.PasswordValidator;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ValidateUserRequestTask extends AbstractApplicationBpmTask {

    private final UserService userService;

    @Autowired
    public ValidateUserRequestTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void executeTask(DelegateExecution de) {
        RegisterUserRequest request = CamundaVariable.getRequest(de);

        assertPasswordStrength(request);
        assertUniqueEmailAddress(request);
    }

    private void assertUniqueEmailAddress(RegisterUserRequest request) {
        User otherUser = userService.findByEmailOrUsernameIgnoreCase(request.getEmail(), request.getEmail());
        if (otherUser != null) {
            throw new BusinessRuleViolationException(String.format("System cannot request. Another user with the email " +
                    "address %s already exists.", request.getEmail()));
        }
    }

    private void assertPasswordStrength(RegisterUserRequest request) {
        PasswordDecision passwordDecision = PasswordValidator.execute(request.getPassword());
        if (!passwordDecision.isValid()) {
            String unmetRequirements = passwordDecision.getCriteria().stream()
                    .filter(c -> !c.isValid())
                    .map(unmetRequirement -> unmetRequirement.getType().value())
                    .collect(Collectors.joining(", "));

            throw new BusinessRuleViolationException(String.format("System cannot complete your request. Your password does not meet " +
                    "requirements. Details: <%s>", unmetRequirements));
        }

    }
}
