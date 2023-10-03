package com.sunshard.gateway.client;

import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "ApplicationFeignClient", url = "${application-address}")
public interface ApplicationFeignClient {

    @PostMapping("/application")
    ResponseEntity<List<LoanOfferDTO>> createLoanOffers(@RequestBody LoanApplicationRequestDTO request);

    @PutMapping("/application/offer")
    ResponseEntity<Void> applyLoanOffer(@RequestBody LoanOfferDTO loanOffer);
}
