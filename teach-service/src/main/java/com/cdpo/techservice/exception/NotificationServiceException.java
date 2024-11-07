package com.cdpo.techservice.exception;

import org.springframework.http.HttpStatus;

public class NotificationServiceException extends TeachServiceException{

    public static final String NOTIFICATION_SERVICE_EXCEPTION = "Notification Service Exception";

    public NotificationServiceException() {
        super(HttpStatus.SERVICE_UNAVAILABLE, NOTIFICATION_SERVICE_EXCEPTION);
    }
}
