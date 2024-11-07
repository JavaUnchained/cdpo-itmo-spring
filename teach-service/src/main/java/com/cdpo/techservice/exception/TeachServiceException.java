package com.cdpo.techservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class TeachServiceException extends RuntimeException{
    private final HttpStatus httpStatus;

    public TeachServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
