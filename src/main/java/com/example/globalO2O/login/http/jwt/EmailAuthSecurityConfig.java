package com.example.globalO2O.login.http.jwt;


import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class EmailAuthSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private TokenProvider tokenProvider;

    public EmailAuthSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        EmailAuthSecurityFilter emailAuthSecurityFilter = new EmailAuthSecurityFilter(tokenProvider);
        builder.addFilterBefore(emailAuthSecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
