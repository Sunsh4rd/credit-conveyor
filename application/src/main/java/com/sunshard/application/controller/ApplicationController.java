package com.sunshard.application.controller;

import com.sunshard.application.model.LoanApplicationRequestDTO;
import com.sunshard.application.model.LoanOfferDTO;
import com.sunshard.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationAPI {

    private static final Logger logger = LogManager.getLogger(ApplicationController.class);

    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        logger.info("Sent request to deal service:\n{}", request);
        List<LoanOfferDTO> loanOffers = applicationService.createLoanOffers(request);
        logger.info("Received loan offers from credit conveyor:\n{}", loanOffers);
        return ResponseEntity.ok(loanOffers);
    }

    @Override
    public ResponseEntity<Void> applyLoanOffer(LoanOfferDTO loanOffer) {
        logger.info("Applying loan offer:\n{}", loanOffer);
        applicationService.applyLoanOffer(loanOffer);
        return ResponseEntity.ok().build();
    }
}
