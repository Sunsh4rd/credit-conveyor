package com.sunshard.deal.controller;

import com.sunshard.deal.client.CreditConveyorFeignClient;
import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.model.CreditDTO;
import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import com.sunshard.deal.model.ScoringDataDTO;
import com.sunshard.deal.model.enums.CreditStatus;
import com.sunshard.deal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculationController implements CalculationAPI {

    private final CreditConveyorFeignClient creditConveyorFeignClient;
    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<CreditDTO> calculateCreditData(
            Long applicationId,
            FinishRegistrationRequestDTO finishRegistrationRequest
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
