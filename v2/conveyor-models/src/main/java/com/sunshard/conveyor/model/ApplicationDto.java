package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Application data transfer object")
public class ApplicationDto {

    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "application id"
    )
    private Long id;

    @Schema(description = "related client data")
    private ClientDto client;

    @Schema(description = "related credit data")
    private CreditDto credit;

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
    private LoanOfferDto appliedOffer;

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
    private List<ApplicationStatusHistoryDto> statusHistory;
}
