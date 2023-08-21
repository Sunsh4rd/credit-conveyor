package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.model.CreditDTO;
import com.sunshard.deal.model.PaymentScheduleElement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditMapperTest {

    @Autowired
    CreditMapper creditMapper;

    @Test
    void dtoToEntity_success() {
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        CreditDTO creditDTO = CreditDTO.builder()
                .amount(BigDecimal.valueOf(300000))
                .psk(BigDecimal.valueOf(8.50))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .monthlyPayment(BigDecimal.valueOf(12000))
                .paymentSchedule(paymentSchedule)
                .rate(BigDecimal.valueOf(12.50))
                .term(12)
                .build();

        Credit credit = Credit.builder()
                .amount(BigDecimal.valueOf(300000))
                .psk(BigDecimal.valueOf(8.50))
                .insuranceEnabled(false)
                .salaryClient(true)
                .monthlyPayment(BigDecimal.valueOf(12000))
                .paymentSchedule(paymentSchedule)
                .rate(BigDecimal.valueOf(12.50))
                .term(12)
                .build();

        assertEquals(credit, creditMapper.dtoToEntity(creditDTO));
    }

    @Test
    void entityToDto_failure() {
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        List<PaymentScheduleElement> paymentSchedule1 = new ArrayList<>(){{
            add(PaymentScheduleElement.builder().build());
            add(PaymentScheduleElement.builder().build());
        }};
        CreditDTO creditDTO = CreditDTO.builder()
                .amount(BigDecimal.valueOf(300000))
                .psk(BigDecimal.valueOf(8.50))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .monthlyPayment(BigDecimal.valueOf(12000))
                .paymentSchedule(paymentSchedule)
                .rate(BigDecimal.valueOf(12.50))
                .term(12)
                .build();

        Credit credit = Credit.builder()
                .amount(BigDecimal.valueOf(300000))
                .psk(BigDecimal.valueOf(8.50))
                .insuranceEnabled(false)
                .salaryClient(true)
                .monthlyPayment(BigDecimal.valueOf(12000))
                .paymentSchedule(paymentSchedule1)
                .rate(BigDecimal.valueOf(12.50))
                .term(12)
                .build();

        assertNotEquals(creditDTO, creditMapper.entityToDto(credit));
    }
}