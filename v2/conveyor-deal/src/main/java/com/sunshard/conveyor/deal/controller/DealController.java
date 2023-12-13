package com.sunshard.conveyor.deal.controller;

import com.sunshard.conveyor.api.deal.DealApi;
import com.sunshard.conveyor.deal.service.DealService;
import com.sunshard.conveyor.model.FinishRegistrationRequestDto;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class DealController implements DealApi {

    private final DealService dealService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> createLoanOffers(LoanApplicationRequestDto request) {
        log.info("Sent request to credit conveyor:\n{}", request);
        List<LoanOfferDto> loanOffers = dealService.createLoanOffers(request);
        log.info("Received loan offers from credit conveyor:\n{}", loanOffers);
        return ResponseEntity.ok(loanOffers);
    }

    @Override
    public ResponseEntity<Void> applyLoanOffer(LoanOfferDto loanOffer) {
        log.info("Applying loan offer:\n{}", loanOffer);
        dealService.applyLoanOffer(loanOffer);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> calculateCreditData(Long applicationId, FinishRegistrationRequestDto finishRegistrationRequest) {
        log.info(
                "Calculating credit data for application id:{} and finish registration data:\n{}",
                applicationId,
                finishRegistrationRequest
        );
        dealService.calculateCreditData(applicationId, finishRegistrationRequest);
        return ResponseEntity.noContent().build();
    }
}
