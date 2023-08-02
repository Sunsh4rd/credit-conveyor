package com.sunshard.conveyor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid data supplied")
public class InvalidApplicationRequestException extends RuntimeException {
}
