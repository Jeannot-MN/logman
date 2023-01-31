package com.jmn.logman.service.validator;

import com.jmn.logman.model.User;
import com.jmn.logman.model.exception.BusinessRuleViolationException;
import com.jmn.logman.model.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserValidator {

    private static final Logger LOG = LoggerFactory.getLogger(UserValidator.class);

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(User user) {
        LOG.info("Validating: " + user);
        Assert.notNull(user.getEmail(), "Email is not specified");
        validateDuplicateUser(user);
    }

    private void validateDuplicateUser(User user) {
        String username = StringUtils.trimToNull(user.getUsername());
        String email = StringUtils.trimToNull(user.getEmail());
        if (username != null) {
            User otherUser = userRepository.findByEmailOrUsernameIgnoreCase(email, username);
            if (otherUser != null && !otherUser.getId().equals(user.getId())) {
                throw new BusinessRuleViolationException(String.format("System exception. User with user name %s already exists."
                        , otherUser.getUsername()));
            }
        }
    }
}
