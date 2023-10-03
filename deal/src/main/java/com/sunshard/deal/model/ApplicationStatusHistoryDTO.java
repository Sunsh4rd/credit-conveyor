package com.sunshard.deal.model;

import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Application status history data")
public class ApplicationStatusHistoryDTO {

    @Schema(
            type = "enum",
            example = "APPROVED",
            description = "current application status"
    )
    private ApplicationStatus status;

    @Schema(
            type = "string",
            format = "date-time",
            example = "2023-10-05",
            description = "timestamp of set status"
    )
    private LocalDateTime time;

    @Schema(
            type = "enum",
            example = "AUTOMATIC",
            description = "application change type"
    )
    private ChangeType changeType;
}
