package com.jmn.logman.service.security;

import com.jmn.logman.service.security.jwt.ApplicationUserDetails;
import com.jmn.logman.service.security.jwt.JwtResponse;
import com.jmn.logman.service.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class AuthenticationService {

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse toToken(ApplicationUserDetails applicationUserDetails) {
        Date expiryDate = createExpirationDate();
        String jwt = jwtUtils.generateJwtToken(applicationUserDetails, expiryDate);
        return new JwtResponse(jwt, expiryDate);
    }

    @NonNull
    private Date createExpirationDate() {
        return Date.from(LocalDate.now().atStartOfDay().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
    }

    public void validateJwtTokenStructure(String token) {
        jwtUtils.validateJwtTokenStructure(token);
    }
}
