package com.sunshard.conveyor.api.core;

import com.sunshard.conveyor.model.ErrorDto;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Tag(name = "Conveyor", description = "Credit conveyor methods")
@RequestMapping("/conveyor")
public interface OfferApi {

    @PostMapping("/offers")
    @Operation(
            summary = "Create loan offers",
            description = "Provide possible loan offers",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loaner's application request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoanApplicationRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoanOfferDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid data provided",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<List<LoanOfferDto>> createLoanOffers(
            @Valid @RequestBody LoanApplicationRequestDto request
    );
}
