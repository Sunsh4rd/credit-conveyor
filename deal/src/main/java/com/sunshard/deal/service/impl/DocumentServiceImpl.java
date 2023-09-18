package com.sunshard.deal.service.impl;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.kafka.KafkaProducer;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.EmailMessage;
import com.sunshard.deal.model.PaymentScheduleElement;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.model.enums.Theme;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final KafkaProducer producer;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    private static final Logger logger = LogManager.getLogger(DocumentServiceImpl.class.getName());

    @Override
    public void createDocuments(Long applicationId) {
        logger.info("Creating documents for application {}", applicationId);
        Application application = getApplicationById(applicationId);
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
        saveApplication(application);
        List<PaymentScheduleElement> paymentSchedule = application.getCredit().getPaymentSchedule();
        EmailMessage message = EmailMessage.builder()
                .applicationId(applicationId)
                .theme(Theme.SEND_DOCUMENTS)
                .address(client.getEmail())
                .build();
        producer.sendMessage(Theme.SEND_DOCUMENTS, message);
        logger.info("Message of creating documents sent");
    }

    @Override
    @Transactional
    public void signRequest(Long applicationId) {
        logger.info("Sending sign request for application {}", applicationId);
        Application application = getApplicationById(applicationId);
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
        Random random = new Random();
        int sesCode = 1000 + random.nextInt(9000);
        application.setSesCode(String.valueOf(sesCode));
        saveApplication(application);
        EmailMessage message = EmailMessage.builder()
                .applicationId(applicationId)
                .theme(Theme.SEND_SES)
                .address(application.getClient().getEmail())
                .build();
        producer.sendMessage(Theme.SEND_SES, message);
        logger.info("Sign request sent");
    }

    @Override
    public void signDocuments(Long applicationId, Integer sesCode) {
        logger.info("Checking ses code and issuing credit for application {}", applicationId);
        Application application = getApplicationById(applicationId);
        String savedSesCode = application.getSesCode();
        if (!savedSesCode.equals(sesCode.toString())) {
            logger.error("Wrong ses code was provided for application {}. Sending denial email", applicationId);
            application.setStatus(ApplicationStatus.CC_DENIED);
            List<ApplicationStatusHistoryDTO> history = application.getStatusHistory();
            history.add(
                    ApplicationStatusHistoryDTO.builder()
                            .status(ApplicationStatus.CC_DENIED)
                            .time(LocalDateTime.now())
                            .changeType(ChangeType.AUTOMATIC)
                            .build()
            );
            EmailMessage message = EmailMessage.builder()
                    .applicationId(applicationId)
                    .address(application.getClient().getEmail())
                    .theme(Theme.APPLICATION_DENIED)
                    .build();
            application.setStatusHistory(history);
            saveApplication(application);
            producer.sendMessage(Theme.APPLICATION_DENIED, message);
            logger.info("Denial email was sent for application {}", applicationId);
        } else {
            logger.info("Provided correct ses code. Sending email for application {}", applicationId);
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
            saveApplication(application);
            EmailMessage message = EmailMessage.builder()
                    .applicationId(applicationId)
                    .theme(Theme.CREDIT_ISSUED)
                    .address(application.getClient().getEmail())
                    .build();
            producer.sendMessage(Theme.CREDIT_ISSUED, message);
            logger.info("Sent message of issuing credit");
        }
    }

    /**
     * Find <i>application</i> by the provided <i>id</i>
     * @param id provided id
     * @return found application
     * @throws ApplicationNotFoundException if no application was found
     * @see Application
     */
    private Application getApplicationById(Long id) {
        logger.info("Searching for application with id: {}", id);
        if (applicationRepository.findById(id).isEmpty()) {
            logger.error("Could not find application with id: {}", id);
            throw new ApplicationNotFoundException(id);
        } else {
            return applicationRepository.findById(id).get();
        }
    }

    /**
     * Save the provided <i>application</i> to the <i>database</i>
     * @param application provided application
     * @see Application
     */
    private void saveApplication(Application application) {
        logger.info("Saving application to the database:\n{}", applicationMapper.entityToDto(application));
        applicationRepository.save(application);
        logger.info("Application saved");
    }
}
