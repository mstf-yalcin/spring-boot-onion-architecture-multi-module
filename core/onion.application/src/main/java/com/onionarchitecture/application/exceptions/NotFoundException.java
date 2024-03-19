package com.onionarchitecture.application.exceptions;
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
