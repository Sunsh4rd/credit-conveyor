package com.sunshard.deal.service;

import com.sunshard.deal.client.CreditConveyorFeignClient;
import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.model.*;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.model.enums.CreditStatus;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.repository.ClientRepository;
import com.sunshard.deal.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for working with credit conveyor database
 */

@Service
@RequiredArgsConstructor
public class DealService {

    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final ClientMapper clientMapper;
    private final CreditMapper creditMapper;
    private final ApplicationMapper applicationMapper;
    private final CreditConveyorFeignClient creditConveyorFeignClient;

    /**
     * Saves the <i>client</i> data  from <i>request</i> into the database
     * @param request loan request data
     * @return client data
     * @see Client
     * @see LoanApplicationRequestDTO
     */
    public Client addClient(LoanApplicationRequestDTO request) {
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
    public Application createApplicationForClient(Client client) {
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
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("No application with id %d", id))
        );
    }

    /**
     * Save the provided <i>application</i> to the <i>database</i>
     * @param application provided application
     * @see Application
     */
    public void saveApplication(Application application) {
        applicationRepository.save(application);
    }

    /**
     * Save the provided <i>credit</i> to the <i>database</i>
     * @param credit provided credit
     * @see Credit
     */
    public void saveCreditData(Credit credit) {
        creditRepository.save(credit);
    }

    @Transactional
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
        Client client = addClient(request);
        List<LoanOfferDTO> loanOffers = creditConveyorFeignClient.createLoanOffers(request).getBody();
        Application application = createApplicationForClient(client);
        loanOffers.forEach(offer -> offer.setApplicationId(application.getApplicationId()));
        return loanOffers;
    }

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

    @Transactional
    public void calculateCreditData(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequest) {
        Application application = getApplicationById(applicationId);
        Client client = application.getClient();
        ScoringDataDTO scoringData = ScoringDataDTO.builder()
                .account(finishRegistrationRequest.getAccount())
                .gender(finishRegistrationRequest.getGender())
                .dependentAmount(finishRegistrationRequest.getDependentAmount())
                .employment(finishRegistrationRequest.getEmployment())
                .birthDate(client.getBirthDate())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .maritalStatus(finishRegistrationRequest.getMaritalStatus())
                .term(application.getAppliedOffer().getTerm())
                .amount(application.getAppliedOffer().getRequestedAmount())
                .isInsuranceEnabled(application.getAppliedOffer().getIsInsuranceEnabled())
                .isSalaryClient(application.getAppliedOffer().getIsSalaryClient())
                .passportNumber(client.getPassport().getNumber())
                .passportSeries(client.getPassport().getSeries())
                .passportIssueBranch(finishRegistrationRequest.getPassportIssueBranch())
                .passportIssueDate(finishRegistrationRequest.getPassportIssueDate())
                .build();
        CreditDTO creditDTO = creditConveyorFeignClient.calculateCreditData(scoringData).getBody();
        Credit credit = creditMapper.dtoToEntity(creditDTO);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        application.setCredit(credit);
        saveCreditData(credit);
        saveApplication(application);
    }
}
