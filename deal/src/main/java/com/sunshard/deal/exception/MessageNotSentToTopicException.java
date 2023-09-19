package com.sunshard.deal.exception;

public class MessageNotSentToTopicException extends RuntimeException {
    public MessageNotSentToTopicException(String message) {
        super(message);
    }
}
