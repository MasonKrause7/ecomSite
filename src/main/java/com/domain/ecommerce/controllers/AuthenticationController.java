package com.domain.ecommerce.controllers;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.AuthenticationControllerException;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.service.AuthenticationService;
import com.domain.ecommerce.service.EmailService;
import com.domain.ecommerce.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;
    private final EmailService emailService;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtTokenService jwtTokenService, EmailService emailService) {
        this.authenticationService = authenticationService;

        this.jwtTokenService = jwtTokenService;
        this.emailService = emailService;
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
    public ResponseEntity<Object> signIn(Authentication authentication) {//add exception
        Map<String, String> tokens = jwtTokenService.getTokens(authentication);
        return ResponseEntity.accepted().body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(Authentication authentication) throws AuthenticationControllerException {//add exception
        logger.debug(authentication.getName());
        try {
            String token = jwtTokenService.refreshAccessToken(authentication);
            return ResponseEntity.accepted().body(token);
        } catch (RuntimeException e) {
            throw new AuthenticationControllerException("Refresh token is expired or does not exist");
        }


    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(String email) throws AuthenticationControllerException {
        if(authenticationService.existingUser("Masongkrause@yahoo.com")) {
           String tempToken = jwtTokenService.getTempToken(authenticationService.findUser("Masongkrause@yahoo.com"));
           emailService.sendMessage("Masongkrause@yahoo.com",tempToken);
            return ResponseEntity.accepted().build();
        } else throw new AuthenticationControllerException("could not find user");



    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(String email) throws AuthenticationControllerException {

        return ResponseEntity.accepted().build();

    }


}