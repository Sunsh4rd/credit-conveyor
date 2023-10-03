package com.sunshard.application.service.impl;

import com.sunshard.application.client.DealFeignClient;
import com.sunshard.application.model.LoanApplicationRequestDTO;
import com.sunshard.application.model.LoanOfferDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    DealFeignClient dealFeignClient;

    @Test
    void createLoanOffers_returnsNull() {
        LoanApplicationRequestDTO request = LoanApplicationRequestDTO.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(18)
                .firstName("first")
                .lastName("last")
                .middleName("middle")
                .email("your@mail.com")
                .birthDate(LocalDate.parse("2000-01-01"))
                .passportSeries("1231")
                .passportNumber("123456")
                .build();
        assertNull(applicationService.createLoanOffers(request));
    }

    @Test
    void applyLoanOffer_verifyTimesDealFeignClientInvoked() {
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
        applicationService.applyLoanOffer(loanOffer);
        Mockito.verify(dealFeignClient, Mockito.times(1)).applyLoanOffer(loanOffer);
    }
}