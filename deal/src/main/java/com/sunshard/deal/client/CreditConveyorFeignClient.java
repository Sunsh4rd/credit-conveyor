package com.sunshard.deal.client;

import com.sunshard.deal.model.CreditDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.model.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "CreditConveyorFeignClient", url = "${conveyor-address}")
public interface CreditConveyorFeignClient {
    @PostMapping("/conveyor/offers")
    ResponseEntity<List<LoanOfferDTO>> createLoanOffers(@RequestBody LoanApplicationRequestDTO request);

    @PostMapping("/conveyor/calculation")
    ResponseEntity<CreditDTO> calculateCreditData(@RequestBody ScoringDataDTO scoringData);
}
