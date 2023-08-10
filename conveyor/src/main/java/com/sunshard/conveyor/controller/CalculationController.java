package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculationController implements CalculationAPI {

    private final CalculationService calculationService;
    private static final Logger logger = LogManager.getLogger(CalculationController.class.getName());

    @Override
    public ResponseEntity<CreditDTO> calculateCreditData(ScoringDataDTO scoringData) {
        logger.info("New scoring data was received:");
        logger.info(scoringData);
        CreditDTO creditData = calculationService.calculateCreditData(scoringData);
        logger.info("Calculated credit data:");
        logger.info(creditData);
        return ResponseEntity.ok(creditData);
    }
}
