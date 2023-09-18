package com.sunshard.deal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Client", description = "Client methods")
public interface ClientAPI {
    @GetMapping("/deal/creditData/{applicationId}")
    ResponseEntity<String> getCreditData(@PathVariable Long applicationId);

    @GetMapping("/deal/getSesCode/{applicationId}")
    ResponseEntity<String> getSesCode(@PathVariable Long applicationId);
}
