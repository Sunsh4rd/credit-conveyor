package com.sunshard.gateway.service.impl;

import com.sunshard.gateway.client.ApplicationFeignClient;
import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    ApplicationFeignClient applicationFeignClient;

    @Test
    void createLoanOffers_success() {
        LoanApplicationRequestDTO loanApplicationRequest = LoanApplicationRequestDTO.builder().build();
        Mockito.when(applicationFeignClient.createLoanOffers(loanApplicationRequest))
                        .thenReturn(ResponseEntity.of(Optional.of(List.of(LoanOfferDTO.builder().build()))));
        applicationService.createLoanOffers(loanApplicationRequest);
        Mockito.verify(applicationFeignClient, Mockito.times(1))
                .createLoanOffers(loanApplicationRequest);
    }

    @Test
    void applyLoanOffer_success() {
        LoanOfferDTO loanOffer = LoanOfferDTO.builder().build();
        applicationService.applyLoanOffer(loanOffer);
        Mockito.verify(applicationFeignClient, Mockito.times(1))
                .applyLoanOffer(loanOffer);
    }
}