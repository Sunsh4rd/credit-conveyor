package com.sunshard.deal.controller;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.model.CreditDTO;
import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Deal", description = "Deal methods")
public interface DealAPI {

    @PostMapping("/deal/application")
    @Operation(summary = "Create loan offers",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided")
            },
            description = "Provide possible loan offers"
    )
    ResponseEntity<List<LoanOfferDTO>> createLoanOffers(@RequestBody LoanApplicationRequestDTO request);

    @Operation(
            summary = "Apply certain loan offer",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully applied"),
                    @ApiResponse(responseCode = "400", description = "Can not apply the loan application")},
            description = "Apply provided loan offer and save it"
    )
    @PutMapping("/deal/offer")
    ResponseEntity<Void> applyLoanOffer(@RequestBody LoanOfferDTO loanOffer);

    @Operation(
            summary = "Calculate credit data",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created"),
                    @ApiResponse(responseCode = "400", description = "You can not apply for the loan")},
            description = "Calculate credit data based on scoring data"
    )
    @PutMapping("/deal/calculate/{applicationId}")
    ResponseEntity<Void> calculateCreditData(
            @PathVariable Long applicationId,
            @RequestBody FinishRegistrationRequestDTO finishRegistrationRequest
    );
}
