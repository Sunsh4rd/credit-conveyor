package com.sunshard.gateway.exception;

public class ApplicationNotFoundException extends RuntimeException{

    public ApplicationNotFoundException() {
        super();
    }

    public ApplicationNotFoundException(Long id) {
        super(String.format("No application with id %d", id));
    }
}
