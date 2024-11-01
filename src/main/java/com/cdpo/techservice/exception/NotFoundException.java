package com.cdpo.techservice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends TeachServiceException {
    private static final String NOT_FOUND = "Not Found";

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
