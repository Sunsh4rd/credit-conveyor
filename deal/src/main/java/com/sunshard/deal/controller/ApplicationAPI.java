package com.sunshard.deal.controller;

import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Deal", description = "Deal methods")
public interface ApplicationAPI {

    @PostMapping("/deal/application")
    @Operation(summary = "Create loan offers",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided")
            },
            description = "Provide possible loan offers"
    )
    ResponseEntity<List<LoanOfferDTO>> createLoanOffers(
            @RequestBody LoanApplicationRequestDTO request
    );
}
