package com.cdpo.techservice.exception;

import com.cdpo.techservice.TechServiceApplication;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends TeachServiceException {

    public static final String NOT_FOUND = "User not found";

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, NOT_FOUND);
    }
}
