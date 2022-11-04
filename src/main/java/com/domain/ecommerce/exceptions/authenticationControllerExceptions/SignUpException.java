package com.domain.ecommerce.exceptions.authenticationControllerExceptions;

public class SignUpException extends Exception{
    public SignUpException() {
        super();
    }

    public SignUpException(String message) {
        super(message);
    }

    public SignUpException(String message, Throwable cause) {
        super(message, cause);
    }
}
