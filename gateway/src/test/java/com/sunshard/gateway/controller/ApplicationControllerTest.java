package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import com.sunshard.gateway.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

    @InjectMocks
    ApplicationController applicationController;

    @Mock
    ApplicationServiceImpl applicationService;

    @Test
    void createLoanOffers_success() {
        Mockito.when(applicationService
                .createLoanOffers(LoanApplicationRequestDTO.builder().build()))
                .thenReturn(List.of(LoanOfferDTO.builder().build(),
                        LoanOfferDTO.builder().build(),
                        LoanOfferDTO.builder().build(),
                        LoanOfferDTO.builder().build()));
        applicationController.createLoanOffers(LoanApplicationRequestDTO.builder().build());
        Mockito.verify(applicationService, Mockito.times(1))
                .createLoanOffers(LoanApplicationRequestDTO.builder().build());
        assertEquals(4, applicationService.createLoanOffers(LoanApplicationRequestDTO.builder().build()).size());
    }

    @Test
    void applyLoanOffer_success() {
        LoanOfferDTO loanOffer = LoanOfferDTO.builder().build();
        applicationController.applyLoanOffer(loanOffer);
        Mockito.verify(applicationService, Mockito.times(1)).applyLoanOffer(loanOffer);
    }
}