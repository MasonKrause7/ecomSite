package com.domain.ecommerce.service;

import com.domain.ecommerce.security.SecurityUser;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
/*
Springs DaoAuthenticationProvider calls an implementation of the UserDetailsService to retrieve username,password and other attributes.
spring offers an in-memory and JDBC implementation of UserDetailsService. Alternatively, your can
define a custom implementation like the class defined below. Class must be marked with @Component,or @Service or etc..
to be registered in the context. Custom implementation must also implement the UserDetailsService interface
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
   /*
   methods called by springs DaoAuthenticationProvider to get user details.
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(username); //find the user you want to authenticate
        SecurityUser securityUser;
        if(user.isPresent()) {
            securityUser = new SecurityUser(user.get());//create a new object of type UserDetails passing in a user to wrap around. This SecurityUser Class implements the UserDetails interface which is required in or order to be used by spring DaoAuthenticationProvider
        } else throw new UsernameNotFoundException("Username " + username + " not found");
        return securityUser;

    }
}
