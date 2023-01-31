package com.jmn.logman.service.user;

import com.jmn.logman.model.User;
import com.jmn.logman.model.repository.UserRepository;
import com.jmn.logman.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public User save(User user) {
        userValidator.validate(user);
        return userRepository.save(user);
    }

    @Override
    public User findByEmailOrUsernameIgnoreCase(String email, String username) {
        return userRepository.findByEmailOrUsernameIgnoreCase(email, username);
    }
}
