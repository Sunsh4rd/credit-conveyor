package com.sunshard.dossier.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "DealFeignClient", url = "${deal-address}")
public interface DealFeignClient {
    @GetMapping("/deal/creditData/{applicationId}")
    ResponseEntity<String> getCreditData(@PathVariable Long applicationId);

    @GetMapping("/deal/getSesCode/{applicationId}")
    ResponseEntity<String> getSesCode(@PathVariable Long applicationId);
}
