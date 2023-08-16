package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.ScoringDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Conveyor", description = "Credit conveyor methods")
public interface CalculationAPI {
    @PostMapping("/conveyor/calculation")
    @Operation(
            summary = "Calculate credit data based on scoring data",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created"),
                    @ApiResponse(responseCode = "400", description = "You can not apply for the loan")},
            description = "Calculate credit data"
    )
    ResponseEntity<CreditDTO> calculateCreditData(
            @Valid @RequestBody ScoringDataDTO scoringData
    );
}
