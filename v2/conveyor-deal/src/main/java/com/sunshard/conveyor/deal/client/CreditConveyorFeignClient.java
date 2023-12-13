package com.sunshard.conveyor.deal.client;

import com.sunshard.conveyor.model.CreditDto;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;
import com.sunshard.conveyor.model.ScoringDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "creditConveyorFeignClient", url = "${conveyor-address}")
public interface CreditConveyorFeignClient {

    @PostMapping("/conveyor/offers")
    ResponseEntity<List<LoanOfferDto>> createLoanOffers(@RequestBody LoanApplicationRequestDto request);

    @PostMapping("/conveyor/calculation")
    ResponseEntity<CreditDto> calculateCreditData(@RequestBody ScoringDataDto scoringData);
}
