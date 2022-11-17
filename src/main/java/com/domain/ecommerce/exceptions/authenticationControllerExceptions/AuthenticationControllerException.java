package com.domain.ecommerce.exceptions.authenticationControllerExceptions;
/*
Exception to be thrown when there's any error in the AuthenticationControllerClass
 */
public class AuthenticationControllerException extends Exception{
    public AuthenticationControllerException() {
        super();
    }

    public AuthenticationControllerException(String message) {
        super(message);
    }

    public AuthenticationControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
