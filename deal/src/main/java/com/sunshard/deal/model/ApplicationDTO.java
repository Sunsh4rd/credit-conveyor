package com.sunshard.deal.model;

import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.model.enums.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Application data transfer object")
public class ApplicationDTO {

    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "application id"
    )
    private Long applicationId;

    @Schema(description = "related client data")
    private Client client;

    @Schema(description = "related credit data")
    private Credit credit;

    @Schema(
            type = "enum",
            example = "PREAPPROVAL",
            description = "application status"
    )
    private ApplicationStatus status;

    @Schema(
            type = "string",
            format = "date-time",
            example = "2023-10-05",
            description = "creation date"
    )
    private LocalDateTime creationDate;

    @Schema(description = "specified offer used in application")
    private LoanOfferDTO appliedOffer;

    @Schema(
            type = "string",
            format = "date-time",
            example = "2023-10-05",
            description = "sign date"
    )
    private LocalDateTime signDate;

    @Schema(
            type = "string",
            description = "session code"
    )
    private String sesCode;

    @Schema(description = "application status history")
    private List<ApplicationStatusHistoryDTO> statusHistory;
}
