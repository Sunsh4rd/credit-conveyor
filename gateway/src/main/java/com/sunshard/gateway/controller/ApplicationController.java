package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import com.sunshard.gateway.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationAPI {

    private final ApplicationService applicationService;

    private static final Logger logger = LogManager.getLogger(ApplicationController.class.getName());

    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        logger.info("Creating loan offers for application request\n{}", request);
        List<LoanOfferDTO> loanOffers = applicationService.createLoanOffers(request);
        return ResponseEntity.ok(loanOffers);
    }

    @Override
    public ResponseEntity<Void> applyLoanOffer(LoanOfferDTO loanOffer) {
        logger.info("Applying loan offer\n{}", loanOffer);
        applicationService.applyLoanOffer(loanOffer);
        return ResponseEntity.ok().build();
    }
}
