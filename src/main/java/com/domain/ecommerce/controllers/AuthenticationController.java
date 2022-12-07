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
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
/*
Handles login and registration of users. All users have a role of "USER".
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8000",allowCredentials = "true")
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final JwtTokenService jwtTokenService;
    private final EmailService emailService;

    private String accessToken = TokenIdentifier.ACESSTOKEN;
    private String refreshToken = TokenIdentifier.REFRESHTOKEN;



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
        generateTokenCookie(authentication, response);
        System.out.println(authentication.getName() + " is logged in");
        return ResponseEntity.accepted().body(authentication.getName() + " is logged in");
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie(accessToken,"");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setSecure(false);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie(refreshToken,"");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
       return ResponseEntity.accepted().body("logged out");
    }



    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(Authentication authentication, HttpServletResponse response) {

        generateTokenCookie(authentication, response);
        return ResponseEntity.accepted().body("success");

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody String email) throws AuthenticationControllerException {
        if (authenticationService.userExist(email)) {
         //   String tempToken = jwtTokenService.getTempToken(authenticationService.findUserByEmail(email));
           // emailService.sendMessage(email, tempToken);
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

    private void generateTokenCookie(Authentication authentication, HttpServletResponse response) {
        Map<String, String> tokens = jwtTokenService.getTokens(authentication);

        Cookie accessTokenCookie = new Cookie(accessToken,tokens.get(accessToken));
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setPath("/");


        Cookie refreshTokenCookie = new Cookie(refreshToken,tokens.get(refreshToken));
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

}