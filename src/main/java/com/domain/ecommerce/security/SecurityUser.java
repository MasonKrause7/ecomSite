package com.domain.ecommerce.security;

import com.domain.ecommerce.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/*
When using username and password for authentication, spring security can use a UserDetailsService.

The UserDetailsService will create a new object of this class, passing in a user, and return it to the DaoAuthenticationProvider.
This class is used by a custom implementation of the UserDetailsService. See JpaUserDetailsService.java for more information and example.

This class wraps around the User object passed in constructor in order to get access to information about the user like authorities, password...etc.
must implement the UserDetailsService
 */

public class SecurityUser implements UserDetails {
    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        String authority = String.valueOf(user.getAuthority());
        authorities.add(new SimpleGrantedAuthority(authority));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
