package com.sunshard.deal.controller;

import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DealController implements DealAPI {

    private final DealService dealService;
    private static final Logger logger = LogManager.getLogger(DealController.class.getName());
    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        logger.info("Sent request to credit conveyor:\n{}", request);
        List<LoanOfferDTO> loanOffers = dealService.createLoanOffers(request);
        logger.info("Received loan offers from credit conveyor:\n{}", loanOffers);
        return ResponseEntity.ok(loanOffers);
    }

    @Override
    public ResponseEntity<Void> applyLoanOffer(LoanOfferDTO loanOffer) {
        logger.info("Applying loan offer:\n{}", loanOffer);
        dealService.applyLoanOffer(loanOffer);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> calculateCreditData(
            Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequest
    ) {
        logger.info(
                "Calculating credit data for application id:{} and finish registration data:\n{}",
                applicationId,
                finishRegistrationRequest
        );
        dealService.calculateCreditData(applicationId, finishRegistrationRequest);
        return ResponseEntity.ok().build();
    }
}
