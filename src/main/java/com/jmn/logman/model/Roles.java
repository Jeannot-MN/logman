package com.jmn.logman.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Roles {
    SYSTEM_ADMIN("https://jmn.com/role/SYSTEM_ADMIN"),
    COMPANY_ADMIN("https://jmn.com/role/COMPANY_ADMIN"),
    ACCOUNTANT("https://jmn.com/role/ACCOUNTANT"),
    DRIVER("https://jmn.com/role/DRIVER");

    private final String uri;
    private final GrantedAuthority authority;

    Roles(String uri) {
        this.uri = uri;
        this.authority = new SimpleGrantedAuthority(uri);
    }

    public String getUri() {
        return uri;
    }

    public GrantedAuthority getAuthority() {
        return authority;
    }
}
