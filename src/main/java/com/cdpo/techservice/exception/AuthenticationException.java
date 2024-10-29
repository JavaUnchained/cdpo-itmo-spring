package com.cdpo.techservice.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends TeachServiceException{
    public AuthenticationException(HttpStatus code, String message) {
        super(code, message);
    }
}
