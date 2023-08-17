package com.sunshard.deal.controller;

import com.sunshard.deal.client.CreditConveyorFeignClient;
import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.model.*;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.model.enums.CreditStatus;
import com.sunshard.deal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DealController implements DealAPI {

    private final CreditConveyorFeignClient creditConveyorFeignClient;
    private final ApplicationService applicationService;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        Client client = applicationService.addClient(request);
        List<LoanOfferDTO> loanOffers = creditConveyorFeignClient.createLoanOffers(request);
        Application application = applicationService.createApplicationForClient(client);
        loanOffers.forEach(offer -> offer.setApplicationId(application.getApplicationId()));
        return ResponseEntity.ok(loanOffers);
    }

    @Override
    public ResponseEntity<Application> applyLoanOffer(LoanOfferDTO loanOffer) {
        ApplicationDTO applicationDTO = ApplicationMapper.INSTANCE.entityToDto(applicationService.getApplicationById(
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
        return ResponseEntity.ok(applicationService.saveApplication(
                ApplicationMapper.INSTANCE.dtoToEntity(applicationDTO)
        ));
    }

    @Override
    public ResponseEntity<CreditDTO> calculateCreditData(
            Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequest
    ) {
        Application application = applicationService.getApplicationById(applicationId);
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
        CreditDTO creditDTO = creditConveyorFeignClient.calculateCreditData(scoringData);
        Credit credit = CreditMapper.INSTANCE.dtoToEntity(creditDTO);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        application.setCredit(credit);
        applicationService.saveCreditData(credit);
        applicationService.saveApplication(application);
        return ResponseEntity.ok(creditDTO);
    }
}
