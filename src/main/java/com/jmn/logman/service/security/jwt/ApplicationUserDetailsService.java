package com.jmn.logman.service.security.jwt;

import com.jmn.logman.model.User;
import com.jmn.logman.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Map User into a spring security ClientDetails Object
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(ApplicationUserDetailsService.class);

    private final UserRepository userRepository;
    //    private final BruteForceProtectionService bruteForceProtectionService;
    private final ApplicationUserDetailsBuilder applicationUserDetailsBuilder;

    @Autowired
    public ApplicationUserDetailsService(UserRepository userRepository
//            , BruteForceProtectionService bruteForceProtectionService
            , ApplicationUserDetailsBuilder applicationUserDetailsBuilder
    ) {
        this.userRepository = userRepository;
//        this.bruteForceProtectionService = bruteForceProtectionService;
        this.applicationUserDetailsBuilder = applicationUserDetailsBuilder;
    }

    @Override
    public ApplicationUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        username = username.toLowerCase();
        /*if (bruteForceProtectionService.isBlocked(username)) {
            String message = "Your account has been locked due to multiple failed login attempts. " +
                    "Please wait 30 minutes and try log back in.";
            log.warn(message);
            throw new AccountTemporarilyLockedException(message);
        }*/

        User user = userRepository.findByEmailOrUsernameIgnoreCase(username, username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return applicationUserDetailsBuilder.build(user);
    }
}
