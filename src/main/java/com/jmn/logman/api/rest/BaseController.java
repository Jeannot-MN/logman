package com.jmn.logman.api.rest;

import com.jmn.logman.service.security.ApplicationUserAuthentication;
import com.jmn.logman.service.security.jwt.ApplicationUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    protected final ApplicationUserDetails loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return determineCurrentUser(authentication);
    }

    private ApplicationUserDetails determineCurrentUser(Authentication authentication) {
        if (authentication instanceof ApplicationUserAuthentication) {
            return (ApplicationUserDetails) ((ApplicationUserAuthentication) authentication).getDetails();
        }
        return null;
    }
}
