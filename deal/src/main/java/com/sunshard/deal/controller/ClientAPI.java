package com.sunshard.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Client", description = "Client methods")
public interface ClientAPI {
    @GetMapping("/deal/creditData/{applicationId}")
    @Operation(summary = "Get credit data for certain application",
            responses = {@ApiResponse(responseCode = "200", description = "Got data successfully"),
                    @ApiResponse(responseCode = "400", description = "Failed to get data")
            },
            description = "Provide possible loan offers"
    )
    ResponseEntity<String> getCreditData(@PathVariable Long applicationId);

    @GetMapping("/deal/getSesCode/{applicationId}")
    @Operation(summary = "Get ses code of certain application",
            responses = {@ApiResponse(responseCode = "200", description = "Got ses code successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided")
            },
            description = "Provide possible loan offers"
    )
    ResponseEntity<String> getSesCode(@PathVariable Long applicationId);
}
