package com.domain.ecommerce.exceptions.authenticationControllerExceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class SignUpExceptionResponse {
    ZonedDateTime zonedDateTime;
    HttpStatus status;
    String message;

    @Override
    public String toString() {
        return "SignUpExceptionResponse{" +
                "zonedDateTime=" + zonedDateTime +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
