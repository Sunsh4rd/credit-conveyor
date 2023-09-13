package com.sunshard.deal.service.impl;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.kafka.KafkaProducer;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.model.*;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.model.enums.CreditStatus;
import com.sunshard.deal.model.enums.Theme;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final KafkaProducer producer;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final CreditMapper creditMapper;

    @Override
    @Transactional
    public void createDocuments(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).get();
        Client client = application.getClient();
        Credit credit = application.getCredit();
        application.setStatus(ApplicationStatus.PREPARE_DOCUMENTS);
        List<ApplicationStatusHistoryDTO> history = application.getStatusHistory();
        history.add(
                ApplicationStatusHistoryDTO.builder()
                        .status(ApplicationStatus.PREPARE_DOCUMENTS)
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .build()
        );
        application.setStatusHistory(history);
        applicationRepository.save(application);
        List<PaymentScheduleElement> paymentSchedule = application.getCredit().getPaymentSchedule();
        System.out.println(application);
        System.out.println(credit);
        System.out.println(client);
        System.out.println(paymentSchedule);
        EmailMessage message = EmailMessage.builder()
                .applicationId(applicationId)
                .theme(Theme.SEND_DOCUMENTS)
                .address(client.getEmail())
                .build();
        producer.sendMessage(Theme.SEND_DOCUMENTS, message);
    }

    @Override
    @Transactional
    public void signRequest(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).get();
        application.setStatus(ApplicationStatus.DOCUMENT_CREATED);
        List<ApplicationStatusHistoryDTO> history = application.getStatusHistory();
        history.add(
                ApplicationStatusHistoryDTO.builder()
                        .status(ApplicationStatus.DOCUMENT_CREATED)
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .build()
        );
        application.setStatusHistory(history);
        applicationRepository.save(application);
        EmailMessage message = EmailMessage.builder()
                .applicationId(applicationId)
                .theme(Theme.SEND_SES)
                .address(application.getClient().getEmail())
                .build();
        producer.sendMessage(Theme.SEND_SES, message);
    }

    @Override
    public void signDocuments(Long applicationId, Integer sesCode) {
        Application application = applicationRepository.findById(applicationId).get();
        application.setStatus(ApplicationStatus.DOCUMENT_SIGNED);
        List<ApplicationStatusHistoryDTO> history = application.getStatusHistory();
        history.add(
                ApplicationStatusHistoryDTO.builder()
                        .status(ApplicationStatus.DOCUMENT_SIGNED)
                        .time(LocalDateTime.now())
                        .changeType(ChangeType.AUTOMATIC)
                        .build()
        );
        application.setStatusHistory(history);
        applicationRepository.save(application);
        EmailMessage message = EmailMessage.builder()
                .applicationId(applicationId)
                .theme(Theme.CREDIT_ISSUED)
                .address(application.getClient().getEmail())
                .build();
        producer.sendMessage(Theme.CREDIT_ISSUED, message);
    }
}
