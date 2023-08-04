package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Conveyor", description = "Credit conveyor methods")
public interface OfferAPI {

    @PostMapping("/conveyor/offers")
    @Operation(summary = "Create loan offers",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided")
            },
            description = "Provide possible loan offers"
    )
    @ResponseBody
    ResponseEntity<List<LoanOfferDTO>> createLoanOffers(
            @Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequest
    );
}
