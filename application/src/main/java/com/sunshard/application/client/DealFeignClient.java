package com.sunshard.application.client;

import com.sunshard.application.model.LoanApplicationRequestDTO;
import com.sunshard.application.model.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "DealFeignClient", url = "${deal-address}")
public interface DealFeignClient {
    @PostMapping("/deal/application")
    ResponseEntity<List<LoanOfferDTO>> createLoanOffers(@RequestBody LoanApplicationRequestDTO request);

    @PutMapping("/deal/offer")
    ResponseEntity<Void> applyLoanOffer(@RequestBody LoanOfferDTO loanOffer);
}
