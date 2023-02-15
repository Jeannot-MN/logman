package com.jmn.logman.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.security.cors")
public class CorsConfig {
    private final List<String> allowedUrls = new ArrayList<>();

    public List<String> getAllowedUrls() {
        return this.allowedUrls;
    }
}