package com.domain.ecommerce.exceptions.authenticationControllerExceptions;

import com.domain.ecommerce.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class AuthenticationControllerExceptionHandler {
    @ExceptionHandler(value = AuthenticationControllerException.class)
    public ResponseEntity<Object> signUpResponse(AuthenticationControllerException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setZonedDateTime(ZonedDateTime.now());
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        System.out.println("bad request user already exists");

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }




}
