package com.sunshard.gateway.client;

import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "ApplicationFeignClient", url = "${application-address}")
public interface ApplicationFeignClient {

    @PostMapping("/application")
    ResponseEntity<List<LoanOfferDTO>> createLoanOffers(@Valid @RequestBody LoanApplicationRequestDTO request);

    @PutMapping("/application/offer")
    ResponseEntity<Void> applyLoanOffer(@Valid @RequestBody LoanOfferDTO loanOffer);
}
