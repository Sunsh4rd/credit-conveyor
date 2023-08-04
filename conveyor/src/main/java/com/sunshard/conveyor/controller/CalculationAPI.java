package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.ScoringDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "Conveyor", description = "Credit conveyor methods")
public interface CalculationAPI {
    @PostMapping("/conveyor/calculation")
    @Operation(
            summary = "make calculations",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created")},
            description = "Calculate credit data"
    )
    @ResponseBody
    ResponseEntity<CreditDTO> calculation(@RequestBody ScoringDataDTO scoringData);
}
