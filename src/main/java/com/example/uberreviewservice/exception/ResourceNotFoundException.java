package com.example.uberreviewservice.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String s) {
        super(s);
    }
}