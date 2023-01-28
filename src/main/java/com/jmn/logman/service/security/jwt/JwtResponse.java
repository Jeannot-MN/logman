package com.jmn.logman.service.security.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.UUID;

// DEVNOTE: Note the below is "very similar" / the same as an oauth token.
@JsonPropertyOrder({ "access_token", "token_type", "expires_in", "scope", "jti" })
public class JwtResponse {

    private static final String BEARER = "Bearer";
    private static final String SCOPE = "user";

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("token_type")
    private final String tokenType = BEARER.toLowerCase();

    @JsonProperty("expires_in")
    private final Date expiresIn;
    private final String jti;

    public JwtResponse(String accessToken, Date expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.jti = UUID.randomUUID().toString();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return SCOPE;
    }

    public String getJti() {
        return jti;
    }
}
