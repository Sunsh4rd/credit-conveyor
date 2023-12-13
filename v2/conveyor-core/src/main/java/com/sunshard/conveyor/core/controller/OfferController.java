package com.sunshard.conveyor.core.controller;

import com.sunshard.conveyor.api.core.OfferApi;
import com.sunshard.conveyor.core.service.OfferService;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class OfferController implements OfferApi {

    private final OfferService offerService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> createLoanOffers(@Valid LoanApplicationRequestDto request) {
        log.info("New application request was received:\n{}", request);
        List<LoanOfferDto> loanOffers = offerService.createLoanOffers(request);
        loanOffers.sort(Comparator.comparing(LoanOfferDto::getRate));
        log.info("Possible loan offers were created:\n{}", loanOffers);
        return ResponseEntity.ok(loanOffers);
    }

}
