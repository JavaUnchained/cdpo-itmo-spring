package com.cdpo.techservice.aspect;

import com.cdpo.techservice.dto.TeachServiceExceptionDTO;
import com.cdpo.techservice.exception.TeachServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAspect extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = TeachServiceException.class)
    protected ResponseEntity<Object> handle(TeachServiceException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new TeachServiceExceptionDTO(ex.getMessage()),
                new HttpHeaders(),
                ex.getHttpStatus(),
                request
        );
    }
}
