package com.jmn.logman.service.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    private final String jwtSecret;
    private final long jwtExpirationMs;

    @Autowired
    public JwtUtils(@Value("${app.security.jwt.secret}") String jwtSecret
            , @Value("${app.security.jwt.expiration-ms}") long jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateJwtToken(ApplicationUserDetails authentication) {
        Date now = new Date();
        return generateJwtToken(authentication, new Date(now.getTime() + jwtExpirationMs));
    }

    public String generateJwtToken(ApplicationUserDetails authentication, Date expiryDate) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("client_id", authentication.getUsername());
        claims.put("authorities", authentication.getAuthorities());

        return Jwts.builder()
                .setSubject((authentication.getUsername()))
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.get("client_id", String.class);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody();
            if (claims == null) return false;

            LocalDateTime expiryDate = claims.getExpiration().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (expiryDate.isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
                throw new ExpiredJwtException(Jwts.jwsHeader(), claims, "System cannot completed request. " +
                        "Credentials have expired, please login in again.");
            }
            return true;

        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
            throw e;
        }
    }

    public void validateJwtTokenStructure(String authToken) {
        authToken = StringUtils.trimToNull(authToken);
        if (authToken == null) {
            throw new IllegalArgumentException("Token is required. Cannot be null or empty.");
        } else if (authToken.split("\\.").length != 3) {
            throw new MalformedJwtException("Token needs to the structure of header.payload.signature");
        }
    }

    public String stripOffSignature(String authToken) {
        validateJwtTokenStructure(authToken);
        return authToken.substring(0, authToken.lastIndexOf('.') + 1);
    }
}
