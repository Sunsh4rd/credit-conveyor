package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.FinishRegistrationRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Deal", description = "Deal methods")
public interface DealAPI {

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

    @PostMapping("/deal/document/{applicationId}/send")
    @Operation(
            summary = "Sending create documents request",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully sent"),
                    @ApiResponse(responseCode = "400", description = "Request could not be sent")},
            description = "Send create documents request"
    )
    ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId);

    @PostMapping("/deal/document/{applicationId}/sign")
    @Operation(
            summary = "Sending sign documents request",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully sent"),
                    @ApiResponse(responseCode = "400", description = "Request could not be sent")},
            description = "Send signing documents request"
    )
    ResponseEntity<Void> signRequest(@PathVariable Long applicationId);

    @PostMapping("/deal/document/{applicationId}/code")
    @Operation(
            summary = "Signing documents with ses code",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully signed"),
                    @ApiResponse(responseCode = "400", description = "Documents were not signed")},
            description = "Signing documents with ses code"
    )
    ResponseEntity<Void> signDocuments(@PathVariable Long applicationId, @RequestBody Integer sesCode);
}
