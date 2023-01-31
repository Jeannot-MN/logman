package com.jmn.logman.service.user;

import com.jmn.logman.model.User;

public interface UserService {

    User save(User user);

    User findByEmailOrUsernameIgnoreCase(String email, String username);
}
