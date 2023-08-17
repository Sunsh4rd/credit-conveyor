package com.sunshard.deal.service.impl;

import com.sunshard.deal.client.CreditConveyorFeignClient;
import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.exception.LoanOffersNotCreatedException;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.mapper.ScoringDataMapper;
import com.sunshard.deal.model.*;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.model.enums.CreditStatus;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.repository.ClientRepository;
import com.sunshard.deal.repository.CreditRepository;
import com.sunshard.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContextException;
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

    /**
     * Calls the <i>feign client</i> to receive <i>loan offers</i> from <i>credit conveyor</i>
     * @param request loan request
     * @return possible <i>loan offers</i>
     * @see LoanApplicationRequestDTO
     * @see LoanOfferDTO
     */
    @Transactional
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
        Client client = addClient(request);
        List<LoanOfferDTO> loanOffers = creditConveyorFeignClient.createLoanOffers(request).getBody();
        Application application = createApplicationForClient(client);
        if (loanOffers != null) {
            loanOffers.forEach(offer -> offer.setApplicationId(application.getApplicationId()));
        } else {
            throw new LoanOffersNotCreatedException("No loan offers were supplied");
        }
        return loanOffers;
    }

    /**
     * Applies the specified <i>loan offer</i> and saves it to the database
     * @param loanOffer specified <i>loan offer</i>
     * @see LoanOfferDTO
     */
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
        saveApplication(applicationMapper.dtoToEntity(applicationDTO));
    }

    /**
     * Calls the <i>feign client</i> to receive calculated <i>credit data</i> based on <i>application</i> and <i>user data</i>
     * @param applicationId <i>application</i> identifier
     * @param finishRegistrationRequest <i>user data</i>
     * @see Application
     * @see FinishRegistrationRequestDTO
     */
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
        Client client = clientMapper.dtoToEntity(request);
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
        return applicationRepository.save(Application.builder()
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
                .build()
        );
    }

    /**
     * Find <i>application</i> by the provided <i>id</i>
     * @param id provided id
     * @return found application
     * @throws IllegalArgumentException if no application was found
     * @see Application
     */
    private Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElseThrow(
                () -> new ApplicationNotFoundException(id)
        );
    }

    /**
     * Save the provided <i>application</i> to the <i>database</i>
     * @param application provided application
     * @see Application
     */
    private void saveApplication(Application application) {
        applicationRepository.save(application);
    }

    /**
     * Save the provided <i>credit</i> to the <i>database</i>
     * @param credit provided credit
     * @see Credit
     */
    private void saveCreditData(Credit credit) {
        creditRepository.save(credit);
    }
}
