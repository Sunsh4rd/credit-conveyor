package com.sunshard.deal.controller;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.model.LoanOfferDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface OfferAPI {
    @PutMapping("/deal/offer")
    ResponseEntity<Application> applyLoanOffer(@RequestBody LoanOfferDTO loanOffer);
}
