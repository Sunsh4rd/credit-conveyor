package com.sunshard.conveyor.deal.controller;

import com.sunshard.conveyor.api.deal.DealApi;
import com.sunshard.conveyor.model.FinishRegistrationRequestDto;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DealController implements DealApi {

    @Override
    public ResponseEntity<List<LoanOfferDto>> createLoanOffers(LoanApplicationRequestDto request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> applyLoanOffer(LoanOfferDto loanOffer) {
        return null;
    }

    @Override
    public ResponseEntity<Void> calculateCreditData(Long applicationId, FinishRegistrationRequestDto finishRegistrationRequest) {
        return null;
    }
}
