package com.cdpo.techservice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends TeachServiceException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
