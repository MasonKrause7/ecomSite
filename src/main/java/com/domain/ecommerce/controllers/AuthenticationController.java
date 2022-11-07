package com.domain.ecommerce.controllers;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.AuthenticationControllerException;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, PasswordEncoder bCryptPasswordEncoder) {
        this.authenticationService = authenticationService;

    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody User user) throws AuthenticationControllerException {
        boolean exists = authenticationService.existingUser(user.getEmail());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/signup").toUriString());
        if (exists) {
            System.out.println("user already exists");
            throw new AuthenticationControllerException("User already exists");
        } else {
            System.out.println(user);
            authenticationService.createUser(user);

            return ResponseEntity.created(uri).body(authenticationService.createUser(user));
        }

    }

    /*
    need to create exception for log in if fails, send back bad request
    user doesnt exist, else create jwt token and send back to client.....good luck
     */
    @PostMapping("/signin")
    public User signIn(Principal principal){
        return authenticationService.findByUserName(principal.getName());
    }
}