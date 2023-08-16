package com.sunshard.deal.controller;

import com.sunshard.deal.model.CreditDTO;
import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Deal", description = "Deal methods")
public interface CalculationAPI {

    @Operation(
            summary = "Calculate credit data",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created"),
                    @ApiResponse(responseCode = "400", description = "You can not apply for the loan")},
            description = "Calculate credit data based on scoring data"
    )
    @PutMapping("/deal/calculate/{applicationId}")
    ResponseEntity<CreditDTO> calculateCreditData(
            @PathVariable Long applicationId,
            @RequestBody FinishRegistrationRequestDTO finishRegistrationRequest
    );
}
