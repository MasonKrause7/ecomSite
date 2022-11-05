package com.domain.ecommerce.security;

import com.domain.ecommerce.models.Roles;
import com.domain.ecommerce.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

public class SecurityAuthority implements GrantedAuthority {
    private final Roles roles;

    public SecurityAuthority(Roles roles) {
        this.roles= roles;
    }


    @Override
    public String getAuthority() {

        return roles.name();
    }
}
