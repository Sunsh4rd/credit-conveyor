package com.sunshard.conveyor.core.controller;

import com.sunshard.conveyor.core.exception.CreditDeniedException;
import com.sunshard.conveyor.model.ErrorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ConveyorExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, CreditDeniedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleInvalidArguments(Exception e) {
        return ResponseEntity
                .badRequest()
                .body(
                        ErrorDto.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .errorMessage(e.getMessage())
                        .build()
                );
    }

}
