package com.domain.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.httpBasic().and()
               .csrf().disable()
               .cors().and()
               .authorizeRequests().mvcMatchers("/api/employees/*").hasRole("ADMIN")
               .antMatchers("/api/users/signup").permitAll()
               .antMatchers("/api/users/signin").authenticated()
               .and()
               .headers().frameOptions().sameOrigin()
               .and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .build();
   }

    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();//replace later with Bcrypt
    }


}
