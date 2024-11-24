package com.example.tests.exception;

public class DataInvalidException extends RuntimeException{
    public DataInvalidException(String message) {
        super(message);
    }
}
