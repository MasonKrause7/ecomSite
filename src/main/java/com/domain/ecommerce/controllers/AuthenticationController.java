package com.domain.ecommerce.controllers;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.SignUpException;
import com.domain.ecommerce.models.Address;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class AuthenticationController {

private final AuthenticationService authenticationService;


@Autowired
public AuthenticationController(AuthenticationService authenticationService, PasswordEncoder bCryptPasswordEncoder) {
    this.authenticationService = authenticationService;

}

@PostMapping ("/signup")
public ResponseEntity<Object> signUp(@RequestBody User user) throws SignUpException {
    boolean exists = authenticationService.existingUser(user.getEmail());
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/signup").toUriString());
    if (exists) {
        throw new SignUpException("User already exists");
    } else {

        authenticationService.createUser(user);
        return ResponseEntity.created(uri).body(authenticationService.createUser(user));
    }

}

/*
need to create exception for log in if fails, send back bad request
user doesnt exist, else create jwt token and send back to client.....good luck
 */
    @PostMapping ("/signin")
    public void signIn(@RequestBody User user) {
        System.out.println("successful log in authentication");

    }

    }