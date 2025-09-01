package com.busreservation.gateway_ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable CSRF
                .httpBasic().disable() // disable default basic auth
                .formLogin().disable() // disable login form
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all requests (gateway will handle JWT)
                );

        return http.build();
    }
}
