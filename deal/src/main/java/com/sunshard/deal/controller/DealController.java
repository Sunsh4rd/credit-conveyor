package com.sunshard.deal.controller;

import com.sunshard.deal.client.CreditConveyorFeignClient;
import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.model.*;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.model.enums.CreditStatus;
import com.sunshard.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
