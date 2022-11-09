package com.domain.ecommerce.controllers;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.AuthenticationControllerException;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.service.AuthenticationService;
import com.domain.ecommerce.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtTokenService jwtTokenService) {
        this.authenticationService = authenticationService;

        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody User user) throws AuthenticationControllerException {
        boolean exists = authenticationService.existingUser(user.getEmail());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/signup").toUriString());
        if (exists) {
            System.out.println("user already exists");
            throw new AuthenticationControllerException("User already exists");
        } else {
            System.out.println("creating user");

            return ResponseEntity.created(uri).body(authenticationService.createUser(user));
        }

    }

    /*
    need to create exception for log in if fails, send back bad request
    user doesn't exist, else create jwt token and send back to client.....good luck
     */
    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(Authentication authentication){//add exception
        Map<String, String> tokens = jwtTokenService.getTokens(authentication);
        return ResponseEntity.accepted().body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object>refreshToken(Authentication authentication) {//add exception
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/refresh").toUriString());
        Jwt jwt = (Jwt) authentication.getCredentials();
        logger.info("Name: " + jwt.getSubject());
        logger.info("TIME ISSUED: "+ Timestamp.from(jwt.getIssuedAt()));
        logger.info("EXPIRES ISSUED: "+ Timestamp.from(jwt.getExpiresAt()));
        return ResponseEntity.accepted().body("ok");//return access token

    }
}