package com.domain.ecommerce.controllers;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.AuthenticationControllerException;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.service.AuthenticationService;
import com.domain.ecommerce.service.EmailService;
import com.domain.ecommerce.service.JwtTokenService;
import com.domain.ecommerce.utils.TokenIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
/*
Handles login and registration of users. All users have a role of "USER".
 */
@RestController
@RequestMapping("/api/users")

public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;
    private final EmailService emailService;

    private final String accessToken = TokenIdentifier.ACESSTOKEN;
    private final String refreshToken = TokenIdentifier.REFRESHTOKEN;
    private final String accessTokenPath = "/";
    private final String refreshTokenPath = "/api/users/refresh";



    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtTokenService jwtTokenService, EmailService emailService) {
        this.authenticationService = authenticationService;

        this.jwtTokenService = jwtTokenService;
        this.emailService = emailService;

    }
    /*
    does not require jwt token for access
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@Valid @RequestBody User user) throws AuthenticationControllerException {
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
        generateTokenCookie(authentication, response);

        System.out.println(authentication.getName() + " is logged in");

        return ResponseEntity.accepted().body(authenticationService.findUserAddressDTObyEmail(authentication.getName()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie(accessToken,null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath(accessTokenPath);
        accessTokenCookie.setMaxAge(0);//deletes cookie from client




        Cookie refreshTokenCookie = new Cookie(refreshToken,null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath(refreshTokenPath);
        refreshTokenCookie.setMaxAge(0);//deletes cookie from client


        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
       return ResponseEntity.accepted().body("logged out");
    }



    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(Authentication authentication, HttpServletResponse response) {

        generateTokenCookie(authentication, response);
        System.out.println("Tokens refreshed");
        return ResponseEntity.accepted().body("Tokens Refreshed");

    }

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
    public ResponseEntity<Object> resetPassword(@RequestBody String newPassword,Authentication authentication,HttpServletResponse response) {
        String email = authentication.getName();
        byte[] bytePassword = Base64.getDecoder().decode(newPassword);
        String password = new String(bytePassword);
        authenticationService.setPassword(email,password);
        generateTokenCookie(authentication,response);
        System.out.println("Password Successfully Reset");
        return ResponseEntity.ok("Password Successfully Reset");

    }

    private void generateTokenCookie(Authentication authentication, HttpServletResponse response) {
        Map<String, String> tokens = jwtTokenService.getTokens(authentication);

        Cookie accessTokenCookie = new Cookie(accessToken,tokens.get(accessToken));
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath(accessTokenPath);


        Cookie publicCookie = new Cookie("logIn", "loggedIn");
        publicCookie.setHttpOnly(false);
        publicCookie.setPath(accessTokenPath);

        Cookie refreshTokenCookie = new Cookie(refreshToken,tokens.get(refreshToken));
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath(refreshTokenPath);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        response.addCookie(publicCookie);
    }



}