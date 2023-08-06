package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculationController implements CalculationAPI {

    private final CalculationService calculationService;

    @Override
    public ResponseEntity<CreditDTO> calculation(ScoringDataDTO scoringData) {
        return new ResponseEntity<>(calculationService.calculation(scoringData), HttpStatus.OK);
    }
}
