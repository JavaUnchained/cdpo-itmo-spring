package com.cdpo.techservice.exception;

import org.springframework.http.HttpStatus;

public class IllegalInputParameters extends TeachServiceException{
    public IllegalInputParameters(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
