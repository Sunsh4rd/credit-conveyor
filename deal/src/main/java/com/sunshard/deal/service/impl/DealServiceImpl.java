package com.sunshard.deal.service.impl;

import com.sunshard.deal.client.CreditConveyorFeignClient;
import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.exception.LoanOffersNotCreatedException;
import com.sunshard.deal.kafka.KafkaProducer;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.mapper.ScoringDataMapper;
import com.sunshard.deal.model.*;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.model.enums.CreditStatus;
import com.sunshard.deal.model.enums.Theme;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.repository.ClientRepository;
import com.sunshard.deal.repository.CreditRepository;
import com.sunshard.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for working with credit conveyor database
 */

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final ClientMapper clientMapper;
    private final CreditMapper creditMapper;
    private final ApplicationMapper applicationMapper;
    private final ScoringDataMapper scoringDataMapper;
    private final CreditConveyorFeignClient creditConveyorFeignClient;
    private final KafkaProducer producer;
    private static final Logger logger = LogManager.getLogger(DealServiceImpl.class.getName());

    /**
     * Calls the <i>feign client</i> to receive <i>loan offers</i> from <i>credit conveyor</i>
     * @param request loan request
     * @return possible <i>loan offers</i>
     * @see LoanApplicationRequestDTO
     * @see LoanOfferDTO
     */

    @Override
    @Transactional
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
        Client client = addClient(request);
        Application application = createApplicationForClient(client);
        ResponseEntity<List<LoanOfferDTO>> loanOffers = creditConveyorFeignClient.createLoanOffers(request);
        List<LoanOfferDTO> resultLoanOffers;
        if (loanOffers != null) {
            resultLoanOffers = loanOffers.getBody();
            resultLoanOffers.forEach(offer -> offer.setApplicationId(application.getApplicationId()));
            logger.info("Loan offers related to the application: {}", application.getApplicationId());
        } else {
            logger.error("Could not get loan offers");
            throw new LoanOffersNotCreatedException("No loan offers were supplied");
        }
        return resultLoanOffers;
    }

    /**
     * Applies the specified <i>loan offer</i> and saves it to the database
     * @param loanOffer specified <i>loan offer</i>
     * @see LoanOfferDTO
     */

    @Override
    @Transactional
    public void applyLoanOffer(LoanOfferDTO loanOffer) {
        ApplicationDTO applicationDTO = applicationMapper.entityToDto(getApplicationById(
                loanOffer.getApplicationId()
        ));
        applicationDTO.setStatus(ApplicationStatus.APPROVED);
        List<ApplicationStatusHistoryDTO> statusHistory = applicationDTO.getStatusHistory();
        statusHistory.add(ApplicationStatusHistoryDTO.builder()
                .status(ApplicationStatus.APPROVED)
                .time(LocalDateTime.now())
                .changeType(ChangeType.AUTOMATIC)
                .build()
        );
        applicationDTO.setStatusHistory(statusHistory);
        applicationDTO.setAppliedOffer(loanOffer);
        producer.sendMessage(Theme.FINISH_REGISTRATION, "It works, id" + applicationDTO.getApplicationId());
        saveApplication(applicationMapper.dtoToEntity(applicationDTO));
    }

    /**
     * Calls the <i>feign client</i> to receive calculated <i>credit data</i> based on <i>application</i> and <i>user data</i>
     * @param applicationId <i>application</i> identifier
     * @param finishRegistrationRequest <i>user data</i>
     * @see Application
     * @see FinishRegistrationRequestDTO
     */

    @Override
    @Transactional
    public void calculateCreditData(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequest) {
        Application application = getApplicationById(applicationId);
        ScoringDataDTO scoringData = scoringDataMapper.from(application, finishRegistrationRequest);
        CreditDTO creditDTO = creditConveyorFeignClient.calculateCreditData(scoringData).getBody();
        Credit credit = creditMapper.dtoToEntity(creditDTO);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        application.setCredit(credit);
        saveCreditData(credit);
        saveApplication(application);
    }

    /**
     * Saves the <i>client</i> data  from <i>request</i> into the database
     * @param request loan request data
     * @return client data
     * @see Client
     * @see LoanApplicationRequestDTO
     */
    private Client addClient(LoanApplicationRequestDTO request) {
        logger.info("Creating client for request:\n{}", request);
        Client client = clientMapper.dtoToEntity(request);
        logger.info("Client created");
        return clientRepository.save(client);
    }

    /**
     * Creates and saves the <i>application</i> for the certain <i>client</i>
     * @param client loaner data
     * @return created application
     * @see Application
     * @see Client
     */
    private Application createApplicationForClient(Client client) {
        logger.info("Creating application for client");
        Application application = Application.builder()
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDateTime.now())
                .client(client)
                .statusHistory(List.of(
                        ApplicationStatusHistoryDTO.builder()
                                .status(ApplicationStatus.PREAPPROVAL)
                                .time(LocalDateTime.now())
                                .changeType(ChangeType.AUTOMATIC)
                                .build()
                ))
                .build();
        logger.info("Application created:\n{}", applicationMapper.entityToDto(application));
        return applicationRepository.save(application);
    }

    /**
     * Find <i>application</i> by the provided <i>id</i>
     * @param id provided id
     * @return found application
     * @throws IllegalArgumentException if no application was found
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

    /**
     * Save the provided <i>credit</i> to the <i>database</i>
     * @param credit provided credit
     * @see Credit
     */
    private void saveCreditData(Credit credit) {
        logger.info("Saving credit to the database:\n{}", creditMapper.entityToDto(credit));
        creditRepository.save(credit);
        logger.info("Credit saved");
    }
}
