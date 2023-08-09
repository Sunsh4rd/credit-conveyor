package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

//    @Mock
//    private OfferRateCalculationService offerRateCalculationService;
//
//    @Mock
//    private TotalAmountCalculationService totalAmountCalculationService;
//
//    @Mock
//    private MonthlyPaymentCalculationService monthlyPaymentCalculationService;
//
//    @Mock
//    private MonthlyRateCalculationService monthlyRateCalculationService;
//
//    @Mock
//    private CreditProperties creditProperties;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Test
    void createLoanOffers() {
        List<LoanOfferDTO> loanOffers = offerService.createLoanOffers(new LoanApplicationRequestDTO());
        assertEquals(4, loanOffers.size());
    }
}