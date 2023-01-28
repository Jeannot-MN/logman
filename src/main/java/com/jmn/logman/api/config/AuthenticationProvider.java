package com.jmn.logman.api.config;

import com.jmn.logman.service.security.ApplicationUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("applicationAuthenticationProvider")
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationProvider(UserDetailsService userDetailsService
            , PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails loadedUser = userDetailsService.loadUserByUsername(username);
        if (loadedUser == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);

        } else if (passwordsMatch(password, loadedUser.getPassword())) {
            return new ApplicationUserAuthentication(loadedUser);
        } else {
            throw new BadCredentialsException("Username or password mismatch.");
        }
    }

    private boolean passwordsMatch(String password, String actualPassword) {
        return passwordEncoder.matches(password, actualPassword);
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}