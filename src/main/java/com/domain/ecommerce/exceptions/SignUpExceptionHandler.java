package com.domain.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class SignUpExceptionHandler {
    @ExceptionHandler(value = SignUpException.class)
    public ResponseEntity<Object> signUpResponse(SignUpException e) {
        SignUpExceptionResponse response = new SignUpExceptionResponse();
        response.setZonedDateTime(ZonedDateTime.now());
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
