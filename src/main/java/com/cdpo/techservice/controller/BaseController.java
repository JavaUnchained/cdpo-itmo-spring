package com.cdpo.techservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
public abstract class BaseController<S> {
    protected final S iService;

    public static <T> ResponseEntity<T> buildNotFound() {
        return ResponseEntity.notFound().build();
    }
}
