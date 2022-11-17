package com.domain.ecommerce.exceptions.authenticationControllerExceptions;

import com.domain.ecommerce.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
/*
catches any AuthenticationControllerException thrown, and creates a new Exception response to be sent back to the front end.
 */
@ControllerAdvice
public class AuthenticationControllerExceptionHandler {
    @ExceptionHandler(value = AuthenticationControllerException.class)
    public ResponseEntity<Object> signUpResponse(AuthenticationControllerException e) {
       if(e.getMessage().equals("User already exists")) {
           ExceptionResponse response = new ExceptionResponse();
           response.setZonedDateTime(ZonedDateTime.now());
           response.setMessage(e.getMessage());
           response.setStatus(HttpStatus.BAD_REQUEST);
           System.out.println("bad request user already exists");

           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
       }
       else if(e.getMessage().equals("could not find user")){
           ExceptionResponse response = new ExceptionResponse();
           response.setZonedDateTime(ZonedDateTime.now());
           response.setMessage(e.getMessage());
           response.setStatus(HttpStatus.NOT_FOUND);
           System.out.println("bad request user does not exist");

           return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
       }
       else{
           System.out.println("ERROR IN EXCEPTION HANDLER - UNRECOGNIZED ERROR MESSAGE");
           ExceptionResponse response = new ExceptionResponse();
           response.setZonedDateTime(ZonedDateTime.now());
           response.setMessage(e.getMessage());
           response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
           System.out.println("bad request user does not exist");

           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }




}
