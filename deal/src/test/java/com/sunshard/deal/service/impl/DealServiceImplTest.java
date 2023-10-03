package com.sunshard.deal.service.impl;

import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @InjectMocks
    DealServiceImpl dealService;

    @Mock
    ApplicationRepository applicationRepository;

    @Test
    void applyLoanOffer_throwsApplicationNotFoundException() {
        LoanOfferDTO loanOffer = LoanOfferDTO.builder()
                .applicationId(14L)
                .requestedAmount(BigDecimal.valueOf(300000))
                .totalAmount(BigDecimal.valueOf(439070.76))
                .term(18)
                .monthlyPayment(BigDecimal.valueOf(24392.82))
                .rate(BigDecimal.valueOf(12))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        assertThrows(ApplicationNotFoundException.class, () -> dealService.applyLoanOffer(loanOffer));
    }
}