package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.model.ApplicationDTO;
import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.model.enums.ApplicationStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ApplicationMapperTest {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Test
    void entityToDto_success() {
        LocalDateTime timeStamp = LocalDateTime.now();
        Credit credit = Credit.builder().build();
        Client client = Client.builder().build();
        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder().build();
        List<ApplicationStatusHistoryDTO> applicationStatusHistory = List.of(
                ApplicationStatusHistoryDTO.builder().build()
        );

        ApplicationDTO applicationDTO = ApplicationDTO.builder()
                .applicationId(1L)
                .appliedOffer(loanOfferDTO)
                .client(client)
                .credit(credit)
                .creationDate(timeStamp)
                .sesCode("19319931931")
                .status(ApplicationStatus.APPROVED)
                .signDate(timeStamp)
                .statusHistory(applicationStatusHistory).build();

        Application application = Application.builder()
                .applicationId(1L)
                .appliedOffer(loanOfferDTO)
                .client(client)
                .credit(credit)
                .creationDate(timeStamp)
                .sesCode("19319931931")
                .status(ApplicationStatus.APPROVED)
                .signDate(timeStamp)
                .statusHistory(applicationStatusHistory).build();

        assertEquals(applicationDTO, applicationMapper.entityToDto(application));
    }

    @Test
    void dtoToEntity_failure() {
        Credit credit = Credit.builder().build();
        Client client = Client.builder().build();
        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder().build();
        List<ApplicationStatusHistoryDTO> applicationStatusHistory = List.of(
                ApplicationStatusHistoryDTO.builder().build()
        );

        ApplicationDTO applicationDTO = ApplicationDTO.builder()
                .applicationId(1L)
                .appliedOffer(loanOfferDTO)
                .client(client)
                .credit(credit)
                .creationDate(LocalDateTime.now())
                .sesCode("19319931931")
                .status(ApplicationStatus.APPROVED)
                .signDate(LocalDateTime.now())
                .statusHistory(applicationStatusHistory).build();

        Application application = Application.builder()
                .applicationId(1L)
                .appliedOffer(loanOfferDTO)
                .client(client)
                .credit(credit)
                .creationDate(LocalDateTime.now())
                .sesCode("19319931931")
                .status(ApplicationStatus.APPROVED)
                .signDate(LocalDateTime.now())
                .statusHistory(applicationStatusHistory).build();

        assertNotEquals(application, applicationMapper.dtoToEntity(applicationDTO));
    }
}