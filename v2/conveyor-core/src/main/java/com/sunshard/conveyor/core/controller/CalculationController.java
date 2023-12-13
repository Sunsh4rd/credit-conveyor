package com.sunshard.conveyor.core.controller;

import com.sunshard.conveyor.api.core.CalculationApi;
import com.sunshard.conveyor.core.service.CalculationService;
import com.sunshard.conveyor.model.CreditDto;
import com.sunshard.conveyor.model.ScoringDataDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CalculationController implements CalculationApi {

    private final CalculationService calculationService;

    @Override
    public ResponseEntity<CreditDto> calculateCreditData(@Valid ScoringDataDto scoringData) {
        log.info("New scoring data was received:\n{}", scoringData);
        CreditDto creditData = calculationService.calculateCreditData(scoringData);
        log.info("Calculated credit data:\n{}", creditData);
        return ResponseEntity.ok(creditData);
    }

}
