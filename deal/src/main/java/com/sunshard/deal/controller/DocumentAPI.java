package com.sunshard.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Documents", description = "Documents methods")
public interface DocumentAPI {

    @PostMapping("/deal/document/{applicationId}/send")
    @Operation(
            summary = "Sending create documents request",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully sent"),
                    @ApiResponse(responseCode = "400", description = "Request could not be sent")},
            description = "Calculate credit data based on scoring data"
    )
    ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId);

    @PostMapping("/deal/document/{applicationId}/sign")
    @Operation(
            summary = "Sending sign documents request",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully sent"),
                    @ApiResponse(responseCode = "400", description = "Request could not be sent")},
            description = "Calculate credit data based on scoring data"
    )
    ResponseEntity<Void> signRequest(@PathVariable Long applicationId);

    @PostMapping("/deal/document/{applicationId}/code")
    @Operation(
            summary = "Signing documents with ses code",
            responses = {@ApiResponse(responseCode = "200", description = "Successfully signed"),
                    @ApiResponse(responseCode = "400", description = "Documents were not signed")},
            description = "Calculate credit data based on scoring data"
    )
    ResponseEntity<Void> signDocuments(@PathVariable Long applicationId, @RequestBody Integer sesCode);
}
