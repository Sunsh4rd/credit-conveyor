package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import com.sunshard.conveyor.service.OfferService;
import com.sunshard.conveyor.service.impl.OfferServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController implements OfferAPI {

    private final OfferService offerService;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO loanApplicationRequest) {
        //log: ConveyorController starts creating loan offers with request body: loanApplicationRequest
        return ResponseEntity.ok(offerService.createLoanOffers(loanApplicationRequest));
    }
}
