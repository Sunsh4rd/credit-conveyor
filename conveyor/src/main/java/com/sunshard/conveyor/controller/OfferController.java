package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import com.sunshard.conveyor.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController implements OfferAPI {

    private final OfferService offerService;
    private static final Logger logger = LogManager.getLogger(OfferController.class.getName());

    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        logger.info("New application request was received:");
        logger.info(request);
        List<LoanOfferDTO> loanOffers = offerService.createLoanOffers(request);
        logger.info("Possible loan offers were created:");
        logger.info(loanOffers);
        return ResponseEntity.ok(loanOffers);
    }
}
