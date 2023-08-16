package com.sunshard.deal.controller;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.model.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Deal", description = "Deal methods")
public interface OfferAPI {

    @Operation(
            summary = "Apply certain loan offer",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully applied"),
                    @ApiResponse(responseCode = "400", description = "Can not apply the loan application")},
            description = "Apply provided loan offer and save it"
    )
    @PutMapping("/deal/offer")
    ResponseEntity<Application> applyLoanOffer(@RequestBody LoanOfferDTO loanOffer);
}
