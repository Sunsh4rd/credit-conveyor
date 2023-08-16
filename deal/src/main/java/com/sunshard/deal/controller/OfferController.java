package com.sunshard.deal.controller;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.model.ApplicationDTO;
import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController implements OfferAPI{

    private final ApplicationService applicationService;
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
}
