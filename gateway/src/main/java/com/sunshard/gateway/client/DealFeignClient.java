package com.sunshard.gateway.client;

import com.sunshard.gateway.model.FinishRegistrationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "DealFeignClient", url = "${deal-address}")
public interface DealFeignClient {

    @PostMapping("/deal/document/{applicationId}/send")
    ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId);

    @PostMapping("/deal/document/{applicationId}/sign")
    ResponseEntity<Void> signRequest(@PathVariable Long applicationId);

    @PostMapping("/deal/document/{applicationId}/code")
    ResponseEntity<Void> signDocuments(@PathVariable Long applicationId, @RequestBody Integer sesCode);

    @PutMapping("/deal/calculate/{applicationId}")
    ResponseEntity<Void> calculateCreditData(
            @PathVariable Long applicationId,
            @RequestBody FinishRegistrationRequestDTO finishRegistrationRequest
    );
}
