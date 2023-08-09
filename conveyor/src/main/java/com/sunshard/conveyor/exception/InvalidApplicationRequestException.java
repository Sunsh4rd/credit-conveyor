package com.sunshard.conveyor.exception;

public class InvalidApplicationRequestException extends RuntimeException {

    public InvalidApplicationRequestException() {
        super();
    }

    public InvalidApplicationRequestException(String message) {
        super(message);
    }
}
