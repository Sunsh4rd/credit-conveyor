package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.ApplicationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Admin", description = "Admin methods")
public interface AdminAPI {

    @GetMapping("/deal/admin/application/{applicationId}")
    @Operation(summary = "Get credit data for certain application",
            responses = {@ApiResponse(responseCode = "200", description = "Got the application successfully"),
                    @ApiResponse(responseCode = "400", description = "Failed to get application")
            },
            description = "Get loan application by id"
    )
    ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long applicationId);

    @GetMapping("/deal/admin/application")
    @Operation(summary = "Get credit data for certain application",
            responses = {@ApiResponse(responseCode = "200", description = "Got all applications successfully"),
                    @ApiResponse(responseCode = "400", description = "Failed to get all applications")
            },
            description = "Get all loan applications"
    )
    ResponseEntity<List<ApplicationDTO>> getAllApplications();
}
