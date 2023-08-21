package com.sunshard.application.controller;

import com.sunshard.application.model.LoanApplicationRequestDTO;
import com.sunshard.application.model.LoanOfferDTO;
import com.sunshard.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationAPI {

    private final ApplicationService applicationService;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        return ResponseEntity.ok(applicationService.createLoanOffers(request));
    }

    @Override
    public ResponseEntity<Void> applyLoanOffer(LoanOfferDTO loanOffer) {
        applicationService.applyLoanOffer(loanOffer);
        return ResponseEntity.ok().build();
    }
}
