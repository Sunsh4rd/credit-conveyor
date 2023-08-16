package com.sunshard.deal.controller;

import com.sunshard.deal.model.CreditDTO;
import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CalculationAPI {
    @PutMapping("/deal/calculate/{applicationId}")
    ResponseEntity<CreditDTO> calculateCreditData(
            @PathVariable Long applicationId,
            @RequestBody FinishRegistrationRequestDTO finishRegistrationRequest
    );
}
