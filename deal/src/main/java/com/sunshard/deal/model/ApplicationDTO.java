package com.sunshard.deal.model;

import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    private Long applicationId;
    private Client client;
    private Credit credit;
    private ApplicationStatus status;
    private LocalDateTime creationDate;
    private LoanOfferDTO appliedOffer;
    private LocalDateTime signDate;
    private String sesCode;
    private List<ApplicationStatusHistoryDTO> statusHistory;
}
