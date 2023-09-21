package com.sunshard.gateway.service;

import com.sunshard.gateway.model.FinishRegistrationRequestDTO;
import org.springframework.http.ResponseEntity;

public interface DealService {
    void calculateCreditData(
            Long applicationId,
            FinishRegistrationRequestDTO finishRegistrationRequest
    );

    void sendDocuments(Long applicationId);

    void signRequest(Long applicationId);

    void signDocuments(Long applicationId, Integer sesCode);
}
