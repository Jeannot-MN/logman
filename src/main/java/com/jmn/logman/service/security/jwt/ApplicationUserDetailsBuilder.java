
package com.jmn.logman.service.security.jwt;

import com.jmn.logman.model.User;
import com.jmn.logman.model.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationUserDetailsBuilder {

    private static final String AUTHORITIES = "https://jmn.com";

    private final ApplicationUserDetailsGroupIdentityBuilder applicationUserDetailsGroupIdentityBuilder;

    @Autowired
    public ApplicationUserDetailsBuilder(ApplicationUserDetailsGroupIdentityBuilder applicationUserDetailsGroupIdentityBuilder) {
        this.applicationUserDetailsGroupIdentityBuilder = applicationUserDetailsGroupIdentityBuilder;
    }

    public ApplicationUserDetails build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserRole userRole : user.getRoles()) {
            String role = String.format("%s/role/%s", AUTHORITIES, StringUtils.removeStart(userRole.getRoleId(), "ROLE_"));
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return applicationUserDetailsGroupIdentityBuilder.build(new ApplicationUserDetails(
                        user.getId()
                        , user.getEmail().toLowerCase()
                        , user.getPassword()
                        , user.getEmail()
                        , user.getFirstName()
                        , user.getLastName()
                        , user.getDisplayName()
                        , authorities
                        , user.getRoles()
                        , user.getProfileImageUri()
                        , user.getMobileNo()
                        , user.getDeactivatedDate()
                        , user.getDeactivatedReasonId()
                )
        );
    }
}
