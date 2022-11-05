package com.domain.ecommerce.exceptions.authenticationControllerExceptions;

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
