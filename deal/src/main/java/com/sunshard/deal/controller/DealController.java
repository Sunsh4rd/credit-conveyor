package com.sunshard.deal.controller;

import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DealController implements DealAPI {

    private final DealService dealService;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        return ResponseEntity.ok(dealService.createLoanOffers(request));
    }

    @Override
    public ResponseEntity<Void> applyLoanOffer(LoanOfferDTO loanOffer) {
        dealService.applyLoanOffer(loanOffer);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> calculateCreditData(
            Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequest
    ) {
        dealService.calculateCreditData(applicationId, finishRegistrationRequest);
        return ResponseEntity.ok().build();
    }
}
