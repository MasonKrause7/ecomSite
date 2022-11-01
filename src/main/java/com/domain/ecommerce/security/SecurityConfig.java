package com.domain.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
 @Bean
 SecurityFilterChain securityConfiguration(HttpSecurity http) throws Exception {
     return http
             .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
             .authorizeRequests(auth -> auth
                     .antMatchers("/h2-console/**").permitAll()
                     .antMatchers("/api/register").permitAll()
                     .antMatchers("/api/login").permitAll()
                     .antMatchers("/api/homepage").permitAll()
                     .anyRequest().authenticated())
             .userDetailsService(userDetailsService)
             .build();






 }

}
