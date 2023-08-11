package com.sunshard.conveyor.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ControllerAdvisor.class.getName());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        logger.error("Invalid data was passed");
        List<ObjectError> allErrors = ex.getAllErrors();
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        body.put("errors", allErrors);
        logger.error("Causing errors: {}", allErrors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(value = {CreditDeniedException.class})
    public ResponseEntity<Object> handleCreditDeniedException(
            CreditDeniedException ex
    ) {
        logger.error("Credit was denied due to: {}", ex.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDate.now());
        return ResponseEntity.badRequest().body(body);
    }
}
