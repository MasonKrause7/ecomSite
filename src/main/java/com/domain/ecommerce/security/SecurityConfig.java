package com.domain.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity
               .cors().disable()//enable cors when done testing
               .csrf().disable()
               .authorizeRequests().mvcMatchers("/api/employees/**").hasRole("ADMIN")
               .mvcMatchers("/api/users/*").permitAll().and()
               .headers().frameOptions().sameOrigin()
               .and()
               .httpBasic().and()//httpBasic adds filter to spring secruity can try and authenticate request using user name and password
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .build();
   }

    @Bean
    public PasswordEncoder passwordEncoder() {

       return new BCryptPasswordEncoder();//replace later with Bcrypt
    }


}
