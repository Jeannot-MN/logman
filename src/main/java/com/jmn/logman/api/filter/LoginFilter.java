package com.jmn.logman.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmn.logman.service.security.AuthenticationService;
import com.jmn.logman.service.security.jwt.ApplicationUserDetails;
import com.jmn.logman.service.security.jwt.AuthenticationRequest;
import com.jmn.logman.service.security.jwt.JwtResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/*import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
//    private BruteForceProtectionService bruteForceProtectionService;

    public LoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/auth/token"));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Basic ")) {
            return null;
        } else {
            AuthenticationRequest authenticationRequest = extractAndDecodeHeader(header);
            /*if (isAccountBlocked(authenticationRequest.getUsername())) {
                throw new AccountTemporarilyLockedException("Your account has been locked due to multiple failed login attempts. "
                        + "Please wait 30 minutes and try log back in.");
            } else {
                return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                        , authenticationRequest.getPassword()));
            }*/
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                    , authenticationRequest.getPassword()));
        }
    }

    /*private boolean isAccountBlocked(String username) {
        return bruteForceProtectionService.isBlocked(username);
    }*/

    private AuthenticationRequest extractAndDecodeHeader(String header) {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        String[] payload = {token.substring(0, delim), token.substring(delim + 1)};

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername(payload[0]);
        authenticationRequest.setPassword(payload[1]);
        return authenticationRequest;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain
            , Authentication authentication) throws IOException {
        ApplicationUserDetails applicationUserDetails = (ApplicationUserDetails) authentication.getDetails();

        JwtResponse jwtResponse = authenticationService.toToken(applicationUserDetails);
        response.setContentType("application/json");
        response.addHeader("authorization", jwtResponse.getAccessToken());

        String json = new ObjectMapper().writeValueAsString(jwtResponse);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();

        // Add the authentication to the Security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /*String principal = authentication.getPrincipal().toString();
        bruteForceProtectionService.loginSucceeded(principal);*/
    }
}