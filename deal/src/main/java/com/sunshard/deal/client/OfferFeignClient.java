package com.sunshard.deal.client;

import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "demoFeign", url = "http://localhost:8080")
public interface OfferFeignClient {
    @PostMapping("/conveyor/offers")
    List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request);
}
