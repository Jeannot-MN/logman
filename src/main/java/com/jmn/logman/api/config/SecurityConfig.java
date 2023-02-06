package com.jmn.logman.api.config;

import com.jmn.logman.api.filter.ApplicationExceptionTranslationFilter;
import com.jmn.logman.api.filter.JwtTokenFilter;
import com.jmn.logman.api.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationExceptionEntryPoint unauthorizedHandler;

    @Autowired
    @Qualifier("applicationAuthenticationProvider")
    private AuthenticationProvider applicationAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(applicationAuthenticationProvider).build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .authenticationProvider(applicationAuthenticationProvider)
                .csrf().disable()
                .headers().frameOptions().deny()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeHttpRequests().requestMatchers("/"
                        , "/auth/token"
                        , "/api/v1/users/register"
                        , "/public/**"
                        , "/error/**"
                ).permitAll()

                .anyRequest().authenticated()
                .and()
                /*.addFilterBefore(new ApplicationLogoutFilter(new ApplicationLogoutHandler(expiredTokenProtectionService))
                        , ChannelProcessingFilter.class)*/
                .addFilterBefore(applicationLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(applicationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(exceptionTranslationFilter(), ExceptionTranslationFilter.class)
//                .addFilterAfter(new JwtTokenExpiredCheckFilter(expiredTokenProtectionService), ExceptionTranslationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        HttpMethod.GET,
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/actuator/**",
                        "/**/favicon.ico",
                        "/**/*.html",
                        "/*.html",
                        "/webjars/**",
                        "/**/favicon.ico",
                        "/**/*.html",
                        "/**/font/**",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.xlsx",
                        "/**/*.woff",
                        "/**/*.woff2",
                        "/",
                        "/csrf",
                        "/error**"
                );
    }


    @Bean
    public ExceptionTranslationFilter exceptionTranslationFilter() {
        ApplicationExceptionTranslationFilter exceptionTranslationFilter
                = new ApplicationExceptionTranslationFilter(new NoopEntryPoint());
        exceptionTranslationFilter.afterPropertiesSet();
        return exceptionTranslationFilter;
    }

    @Bean
    public LoginFilter applicationLoginFilter(AuthenticationManager authenticationManager) throws Exception {
        return new LoginFilter(authenticationManager);
    }

    @Bean
    public JwtTokenFilter applicationJwtTokenFilter() {
        return new JwtTokenFilter();
    }
}
