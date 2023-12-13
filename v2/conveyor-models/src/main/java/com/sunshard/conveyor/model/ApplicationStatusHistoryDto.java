package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.ApplicationStatus;
import com.sunshard.conveyor.model.enums.ChangeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Application status history data")
public class ApplicationStatusHistoryDto {

    @Schema(
            type = "enum",
            example = "APPROVED",
            description = "current application status"
    )
    @NotNull
    private ApplicationStatus status;

    @Schema(
            type = "string",
            format = "date-time",
            example = "2023-10-05",
            description = "timestamp of set status"
    )
    @NotNull
    private LocalDateTime time;

    @Schema(
            type = "enum",
            example = "AUTOMATIC",
            description = "application change type"
    )
    @NotNull
    private ChangeType changeType;
}
