package com.sunshard.conveyor.deal.service.impl;

import com.sunshard.conveyor.deal.client.CreditConveyorFeignClient;
import com.sunshard.conveyor.deal.entity.Application;
import com.sunshard.conveyor.deal.entity.Client;
import com.sunshard.conveyor.deal.entity.Credit;
import com.sunshard.conveyor.deal.exception.ApplicationNotFoundException;
import com.sunshard.conveyor.deal.exception.LoanOffersNotCreatedException;
import com.sunshard.conveyor.deal.mapper.ApplicationMapper;
import com.sunshard.conveyor.deal.mapper.ClientMapper;
import com.sunshard.conveyor.deal.mapper.CreditMapper;
import com.sunshard.conveyor.deal.mapper.ScoringDataMapper;
import com.sunshard.conveyor.deal.repository.ApplicationRepository;
import com.sunshard.conveyor.deal.repository.ClientRepository;
import com.sunshard.conveyor.deal.repository.CreditRepository;
import com.sunshard.conveyor.deal.service.DealService;
import com.sunshard.conveyor.model.*;
import com.sunshard.conveyor.model.enums.ApplicationStatus;
import com.sunshard.conveyor.model.enums.ChangeType;
import com.sunshard.conveyor.model.enums.CreditStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DealServiceImpl implements DealService {

    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final ClientMapper clientMapper;
    private final CreditMapper creditMapper;
    private final ApplicationMapper applicationMapper;
    private final ScoringDataMapper scoringDataMapper;
    private final CreditConveyorFeignClient creditConveyorFeignClient;

    @Override
    @Transactional
    public List<LoanOfferDto> createLoanOffers(LoanApplicationRequestDto request) {
        Client client = addClient(request);
        Application application = createApplicationForClient(client);
        ResponseEntity<List<LoanOfferDto>> loanOffers = creditConveyorFeignClient.createLoanOffers(request);
        List<LoanOfferDto> resultLoanOffers;
        if (loanOffers != null) {
            resultLoanOffers = loanOffers.getBody();
            resultLoanOffers.forEach(offer -> offer.setApplicationId(application.getId()));
            log.info("Loan offers related to the application: {}", application.getId());
        } else {
            log.error("Could not get loan offers");
            throw new LoanOffersNotCreatedException("No loan offers were supplied");
        }
        return resultLoanOffers;
    }

    @Override
    @Transactional
    public void applyLoanOffer(LoanOfferDto loanOffer) {
        ApplicationDto applicationDto = applicationMapper.entityToDto(getApplicationById(
                loanOffer.getApplicationId()
        ));
        applicationDto.setStatus(ApplicationStatus.APPROVED);
        List<ApplicationStatusHistoryDto> statusHistory = applicationDto.getStatusHistory();
        statusHistory.add(ApplicationStatusHistoryDto.builder()
                .status(ApplicationStatus.APPROVED)
                .time(LocalDateTime.now())
                .changeType(ChangeType.AUTOMATIC)
                .build()
        );
        applicationDto.setStatusHistory(statusHistory);
        applicationDto.setAppliedOffer(loanOffer);
//        TODO: uncomment when kafka arrives
//        EmailMessage message = EmailMessage.builder()
//                .address(applicationDto.getClient().getEmail())
//                .theme(Theme.FINISH_REGISTRATION)
//                .applicationId(applicationDto.getId())
//                .build();
//        producer.sendMessage(Theme.FINISH_REGISTRATION, message);
        saveApplication(applicationMapper.dtoToEntity(applicationDto));
    }

    @Override
    @Transactional
    public void calculateCreditData(Long applicationId, FinishRegistrationRequestDto finishRegistrationRequest) {
        Application application = getApplicationById(applicationId);
        ScoringDataDto scoringData = scoringDataMapper.from(application, finishRegistrationRequest);
        CreditDto creditDto = creditConveyorFeignClient.calculateCreditData(scoringData).getBody();
        Credit credit = creditMapper.dtoToEntity(creditDto);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        application.setCredit(credit);
        //        TODO: uncomment when kafka arrives
//        EmailMessage message = EmailMessage.builder()
//                .address(application.getClient().getEmail())
//                .theme(Theme.CREATE_DOCUMENTS)
//                .applicationId(applicationId)
//                .build();
        saveCreditData(credit);
        saveApplication(application);
//        producer.sendMessage(Theme.CREATE_DOCUMENTS, message);
    }

    private Client addClient(LoanApplicationRequestDto request) {
        log.info("Creating client for request:\n{}", request);
        Client client = clientMapper.dtoToEntity(request);
        log.info("Client created");
        return clientRepository.save(client);
    }

    private Application createApplicationForClient(Client client) {
        log.info("Creating application for client");
        Application application = Application.builder()
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDateTime.now())
                .client(client)
                .statusHistory(List.of(
                        ApplicationStatusHistoryDto.builder()
                                .status(ApplicationStatus.PREAPPROVAL)
                                .time(LocalDateTime.now())
                                .changeType(ChangeType.AUTOMATIC)
                                .build()
                ))
                .build();
        log.info("Application created:\n{}", applicationMapper.entityToDto(application));
        return applicationRepository.save(application);
    }

    private Application getApplicationById(Long id) {
        log.info("Searching for application with id: {}", id);
        if (applicationRepository.findById(id).isEmpty()) {
            log.error("Could not find application with id: {}", id);
            throw new ApplicationNotFoundException(id);
        } else {
            return applicationRepository.findById(id).get();
        }
    }

    private void saveApplication(Application application) {
        log.info("Saving application to the database:\n{}", applicationMapper.entityToDto(application));
        applicationRepository.save(application);
        log.info("Application saved");
    }

    private void saveCreditData(Credit credit) {
        log.info("Saving credit to the database:\n{}", creditMapper.entityToDto(credit));
        creditRepository.save(credit);
        log.info("Credit saved");
    }
}
