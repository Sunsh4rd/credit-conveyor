package com.sunshard.conveyor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You can not apply for the loan")
public class CreditDeniedException extends RuntimeException {
}
