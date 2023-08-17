package com.sunshard.deal.exception;

public class ApplicationNotFoundException extends RuntimeException{

    public ApplicationNotFoundException(Long id) {
        super(String.format("No application with id %d", id));
    }
}
