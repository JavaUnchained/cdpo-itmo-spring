package com.cdpo.techservice.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends TeachServiceException{
    public AuthenticationException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
