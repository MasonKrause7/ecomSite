package com.domain.ecommerce.controllers;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.AuthenticationControllerException;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.service.AuthenticationService;
import com.domain.ecommerce.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;
    private final JwtDecoder jwtDecoder;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, PasswordEncoder bCryptPasswordEncoder, JwtTokenService jwtTokenService, JwtDecoder jwtDecoder) {
        this.authenticationService = authenticationService;
        this.jwtTokenService = jwtTokenService;
        this.jwtDecoder = jwtDecoder;
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
    public ResponseEntity<Object> signIn(Authentication authentication){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/signin").toUriString());
        String token = jwtTokenService.generateAccessToken(authentication);

        return ResponseEntity.created(uri).body(token);
    }

    @PostMapping("/refresh")
    public String refreshToken(Authentication authentication) {//test
        Jwt jwt = (Jwt) authentication.getCredentials();
        logger.info("Name: " + jwt.getSubject());
        logger.info("TIME ISSUED: "+ Timestamp.from(jwt.getIssuedAt()));
        logger.info("EXPIRES ISSUED: "+ Timestamp.from(jwt.getExpiresAt()));
        return "request complete";

    }
}