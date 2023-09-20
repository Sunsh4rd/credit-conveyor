package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Application", description = "Application methods")
public interface ApplicationAPI {

    @PostMapping("/application")
    @Operation(summary = "Create loan offers",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided")
            },
            description = "Provide possible loan offers"
    )
    ResponseEntity<List<LoanOfferDTO>> createLoanOffers(@Valid @RequestBody LoanApplicationRequestDTO request);

    @PutMapping("/application/offer")
    @Operation(
            summary = "Apply certain loan offer",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully applied"),
                    @ApiResponse(responseCode = "400", description = "Can not apply the loan application")},
            description = "Apply provided loan offer and save it"
    )
    ResponseEntity<Void> applyLoanOffer(@Valid @RequestBody LoanOfferDTO loanOffer);
}
