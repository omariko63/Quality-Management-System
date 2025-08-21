package com.example.quality_management_service.management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/api/users/validate").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtTokenVerifier(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
