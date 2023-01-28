package com.jmn.logman.service.security.jwt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


@Component
public class ApplicationUserDetailsGroupIdentityBuilder {

    public ApplicationUserDetails build(ApplicationUserDetails user) {
        addUserIdentity(user);
        addRolesIdentity(user);
        return user;
    }

    private void addUserIdentity(ApplicationUserDetails user) {
        if (StringUtils.isNotBlank(user.getUsername())) {
            String username = user.getUsername().toUpperCase();
            if (!user.getGroupIdentities().contains(username)) {
                user.addGroupIdentity(username);
            }
        }
    }

    private void addRolesIdentity(ApplicationUserDetails user) {
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            user.addGroupIdentity(grantedAuthority.getAuthority().toUpperCase());
        }
    }
}
