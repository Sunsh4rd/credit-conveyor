package com.sunshard.gateway.controller;

import com.sunshard.gateway.client.DealFeignClient;
import com.sunshard.gateway.model.ApplicationDTO;
import com.sunshard.gateway.model.FinishRegistrationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DealController {

    private final DealFeignClient dealFeignClient;

    @GetMapping("/deal/admin/application/{applicationId}")
    ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long applicationId) {
        return dealFeignClient.getApplicationById(applicationId);
    }

    @GetMapping("/deal/admin/application")
    ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        return dealFeignClient.getAllApplications();
    }

    @PostMapping("/deal/document/{applicationId}/send")
    ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId) {
        return dealFeignClient.sendDocuments(applicationId);
    }

    @PostMapping("/deal/document/{applicationId}/sign")
    ResponseEntity<Void> signRequest(@PathVariable Long applicationId) {
        return dealFeignClient.signRequest(applicationId);
    }

    @PostMapping("/deal/document/{applicationId}/code")
    ResponseEntity<Void> signDocuments(@PathVariable Long applicationId, @RequestBody Integer sesCode) {
        return dealFeignClient.signDocuments(applicationId, sesCode);
    }

    @PutMapping("/deal/calculate/{applicationId}")
    ResponseEntity<Void> calculateCreditData(
            @PathVariable Long applicationId,
            @RequestBody FinishRegistrationRequestDTO finishRegistrationRequest
    ) {
        return dealFeignClient.calculateCreditData(applicationId, finishRegistrationRequest);
    }
}
