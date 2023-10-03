package com.sunshard.conveyor.exception;

public class CreditDeniedException extends RuntimeException {

    public CreditDeniedException() {
        super();
    }

    public CreditDeniedException(String message) {
        super(message);
    }
}
