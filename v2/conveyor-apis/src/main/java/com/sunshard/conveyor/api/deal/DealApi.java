package com.sunshard.conveyor.api.deal;

import com.sunshard.conveyor.model.ErrorDto;
import com.sunshard.conveyor.model.FinishRegistrationRequestDto;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Deal", description = "Deal methods")
@RequestMapping("/deal")
public interface DealApi {

    @PostMapping("/application")
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
    ResponseEntity<List<LoanOfferDto>> createLoanOffers(@RequestBody LoanApplicationRequestDto request);

    @Operation(
            summary = "Apply certain loan offer",
            description = "Apply provided loan offer and save it",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The loan offer chosen for application",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoanOfferDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully applied",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Can not apply the loan application",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PutMapping("/offer")
    ResponseEntity<Void> applyLoanOffer(@RequestBody LoanOfferDto loanOffer);

    @Operation(
            summary = "Calculate credit data",
            description = "Calculate credit data based on scoring data",
            parameters = @Parameter(
                    name = "applicationId",
                    required = true,
                    description = "Identifier of the application",
                    in = ParameterIn.PATH,
                    schema = @Schema(implementation = Long.class)
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loaner's data for finishing registration",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FinishRegistrationRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully calculated",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "You can not apply for the loan",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    @PutMapping("/calculate/{applicationId}")
    ResponseEntity<Void> calculateCreditData(
            @PathVariable Long applicationId,
            @RequestBody FinishRegistrationRequestDto finishRegistrationRequest
    );
}
