package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class CalculationController implements CalculationAPI {

    private final CalculationService calculationService;
    private static final Logger logger = LoggerFactory.getLogger(CalculationController.class);

    @Override
    public ResponseEntity<CreditDTO> calculateCreditData(ScoringDataDTO scoringData) {
        CreditDTO credit = null;
        try {
            logger.info(
                    "CalculationController received scoring data:\n{} at {}",
                    scoringData,
                    LocalDateTime.now()
            );
            credit = calculationService.calculateCreditData(scoringData);
            logger.info(
                    "New credit data was created:\n{} at {}",
                    credit,
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(credit, HttpStatus.OK);
        } catch (RuntimeException exception) {
            logger.info(
                    "You can not apply for the loan {}",
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(credit, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CreditDTO> handleMethodArgumentNotValidException() {
        logger.info(
                "You can not apply for the loan {}",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
