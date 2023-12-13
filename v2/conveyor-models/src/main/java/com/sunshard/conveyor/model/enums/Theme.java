package com.sunshard.conveyor.model.enums;

import lombok.Getter;

@Getter
public enum Theme {
    FINISH_REGISTRATION("finish-registration"),
    CREATE_DOCUMENTS("create-documents"),
    SEND_DOCUMENTS("send-documents"),
    SEND_SES("send-ses"),
    CREDIT_ISSUED("credit-issued"),
    APPLICATION_DENIED("application-denied");

    private final String name;

    Theme(String name) {
        this.name = name;
    }

}
