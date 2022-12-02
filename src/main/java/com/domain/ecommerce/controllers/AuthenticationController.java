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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
/*
Handles login and registration of users. All users have a role of "USER".
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;
    private final EmailService emailService;



    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtTokenService jwtTokenService, EmailService emailService, PasswordEncoder bCryptPasswordEncoder) {
        this.authenticationService = authenticationService;

        this.jwtTokenService = jwtTokenService;
        this.emailService = emailService;

    }
    /*
    does not require jwt token for access
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody User user) throws AuthenticationControllerException {
        boolean exists = authenticationService.userExist(user.getEmail());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/signup").toUriString());
        if (exists) {
            throw new AuthenticationControllerException("User already exists");
        } else {

            return ResponseEntity.created(uri).body(authenticationService.createUser(user));
        }

    }


    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(Authentication authentication,HttpServletResponse response) {
        System.out.println("LOGGING IN");
        Map<String, String> tokens = jwtTokenService.getTokens(authentication);
        Cookie accessTokenCookie = new Cookie("accesstoken",tokens.get("accesstoken"));
        accessTokenCookie.setHttpOnly(true);

        Cookie refreshTokenCookie = new Cookie("refreshtoken",tokens.get("accesstoken"));
        refreshTokenCookie.setHttpOnly(true);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        System.out.println("cookie added to response");
        return ResponseEntity.accepted().body("success");
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(Authentication authentication) throws AuthenticationControllerException {

        try {
            String token = jwtTokenService.refreshAccessToken(authentication);
            return ResponseEntity.accepted().body(token);
        } catch (RuntimeException e) {
            throw new AuthenticationControllerException("Refresh token is expired or does not exist");
        }


    }
    /*
    does not require jwt token for access.

     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody String email) throws AuthenticationControllerException {
        if (authenticationService.userExist(email)) {
            String tempToken = jwtTokenService.getTempToken(authenticationService.findUserByEmail(email));
            emailService.sendMessage(email, tempToken);
            Map<String, String> respMap = new HashMap<>();
            respMap.put("msg", "email sent");
            return ResponseEntity.ok(respMap);
        } else throw new AuthenticationControllerException("could not find user");


    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody String newPassword,Authentication authentication) {
        String email = authentication.getName();
        byte[] bytePassword = Base64.getDecoder().decode(newPassword);
        String password = new String(bytePassword);
        authenticationService.setPassword(email,password);
        Map<String, String> tokens = jwtTokenService.getTokens(authentication);
        return ResponseEntity.ok(tokens);

    }

}