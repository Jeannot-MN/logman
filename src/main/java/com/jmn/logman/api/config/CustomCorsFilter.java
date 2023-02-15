package com.jmn.logman.api.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomCorsFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomCorsFilter.class);

    private static final String[] SWAGGER_RESOURCES = {"/", "swagger", "api-docs", "csrf", "/error"};

    @Autowired
    private CorsConfig corsConfig;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("origin");

        if (isOriginAllowed(request)) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "content-type, access-control-allow-origin, authorization");


            // Short circuit filter chain if this is preflight request
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            // Continue to process request
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, String.format("Request from origin [%s] not allowed.", origin));
        }
    }

    private boolean isOriginAllowed(HttpServletRequest request) {
        String origin = request.getHeader("origin");
        List<String> allowedUrls = corsConfig.getAllowedUrls();
        if (allowedUrls.size() == 1 && allowedUrls.iterator().next().equals("*")) {
            LOG.trace(String.format("allowedUrls defined as wildcard [*] so origin [%s] allowed.", origin));
            return true;
        } else if (StringUtils.isBlank(origin)) {
            String requestURI = request.getRequestURI();
            if (isPathAllowed(requestURI)) {
                LOG.trace("origin null or empty but path [%s] allowed.");
                return true;
            } else {
                LOG.trace("origin null or empty but path [%s] not allowed.");
                return false;
            }

        } else if (allowedUrls.contains(origin)) {
            LOG.trace(String.format("origin [%s] in %s: allowed.", origin, allowedUrls));
            return true;
        } else {
            LOG.trace(String.format("origin [%s] not in %s: deny.", origin, allowedUrls));
            return false;
        }
    }

    private boolean isPathAllowed(String contextPath) {
        if (contextPath != null) {
            for (String swaggerResource : SWAGGER_RESOURCES) {
                if (contextPath.toLowerCase().contains(swaggerResource)) {
                    LOG.trace(String.format("Path [%s] contains \"%s\": allowed.", contextPath, swaggerResource));
                    return true;
                }
            }
        }
        LOG.trace(String.format("Path [%s] does not contain \"%s\": deny.", contextPath, Arrays.toString(SWAGGER_RESOURCES)));
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
