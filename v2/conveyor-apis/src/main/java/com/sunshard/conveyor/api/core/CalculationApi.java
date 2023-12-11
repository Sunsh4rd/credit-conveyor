package com.sunshard.conveyor.api.core;

import com.sunshard.conveyor.model.CreditDto;
import com.sunshard.conveyor.model.ErrorDto;
import com.sunshard.conveyor.model.ScoringDataDto;
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

@Tag(name = "Conveyor", description = "Credit conveyor methods")
@RequestMapping("/conveyor")
public interface CalculationApi {
    @PostMapping("/calculation")
    @Operation(
            summary = "Calculate credit data based on scoring data",
            description = "Calculate credit data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loaner's scoring data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ScoringDataDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreditDto.class)
                            )
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
    ResponseEntity<CreditDto> calculateCreditData(
            @Valid @RequestBody ScoringDataDto scoringData
    );
}
