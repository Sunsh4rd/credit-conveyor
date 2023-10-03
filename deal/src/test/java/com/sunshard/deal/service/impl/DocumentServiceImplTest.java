package com.sunshard.deal.service.impl;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.EmailMessage;
import com.sunshard.deal.model.enums.Theme;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @InjectMocks
    DocumentServiceImpl documentService;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    KafkaProducerImpl kafkaProducer;

    @Mock
    CreditRepository creditRepository;

    @Test
    void createDocuments_success() {
        Mockito.when(applicationRepository.findById(160L)).thenReturn(Optional.of(Application.builder()
                .applicationId(160L)
                        .client(Client.builder()
                                .email("e@mail.com")
                                .build())
                .sesCode("1442")
                        .statusHistory(new ArrayList<>() {{
                            add(ApplicationStatusHistoryDTO.builder().build());
                        }})
                .build()));
        documentService.createDocuments(160L);
        Mockito.verify(applicationRepository, Mockito.times(2)).findById(160L);
        Mockito.verify(kafkaProducer, Mockito.times(1))
                .sendMessage(Theme.SEND_DOCUMENTS, EmailMessage.builder()
                        .applicationId(160L)
                        .theme(Theme.SEND_DOCUMENTS)
                        .address("e@mail.com")
                        .build());
    }

    @Test
    void signRequest_success() {
        Mockito.when(applicationRepository.findById(160L)).thenReturn(Optional.of(Application.builder()
                .applicationId(160L)
                .client(Client.builder()
                        .email("e@mail.com")
                        .build())
                .sesCode("1442")
                .statusHistory(new ArrayList<>() {{
                    add(ApplicationStatusHistoryDTO.builder().build());
                }})
                .build()));
        documentService.signRequest(160L);
        Mockito.verify(applicationRepository, Mockito.times(2)).findById(160L);
        Mockito.verify(kafkaProducer, Mockito.times(1))
                .sendMessage(Theme.SEND_SES, EmailMessage.builder()
                        .applicationId(160L)
                        .theme(Theme.SEND_SES)
                        .address("e@mail.com")
                        .build());
    }

    @Test
    void signDocuments_success() {
        Mockito.when(applicationRepository.findById(160L)).thenReturn(Optional.of(Application.builder()
                .applicationId(160L)
                .client(Client.builder()
                        .email("e@mail.com")
                        .build())
                .sesCode("1442")
                .statusHistory(new ArrayList<>() {{
                    add(ApplicationStatusHistoryDTO.builder().build());
                }})
                        .credit(Credit.builder().build())
                .build()));
        documentService.signDocuments(160L, 1442);
        Mockito.verify(applicationRepository, Mockito.times(2)).findById(160L);
        Mockito.verify(kafkaProducer, Mockito.times(1))
                .sendMessage(Theme.CREDIT_ISSUED, EmailMessage.builder()
                        .applicationId(160L)
                        .theme(Theme.CREDIT_ISSUED)
                        .address("e@mail.com")
                        .build());
    }
}