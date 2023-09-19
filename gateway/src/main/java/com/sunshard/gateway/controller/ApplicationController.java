package com.sunshard.gateway.controller;

import com.sunshard.gateway.client.ApplicationFeignClient;
import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationFeignClient applicationFeignClient;

    @PostMapping("/application")
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(@Valid @RequestBody LoanApplicationRequestDTO request) {
        return applicationFeignClient.createLoanOffers(request);
    }

    @PutMapping("/application/offer")
    public ResponseEntity<Void> applyLoanOffer(@Valid @RequestBody LoanOfferDTO loanOffer) {
        return applicationFeignClient.applyLoanOffer(loanOffer);
    }
}
