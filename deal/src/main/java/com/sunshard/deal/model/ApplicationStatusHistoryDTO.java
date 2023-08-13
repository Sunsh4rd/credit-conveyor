package com.sunshard.deal.model;

import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationStatusHistoryDTO {
    private ApplicationStatus status;
    private LocalDateTime time;
    private ChangeType changeType;
}
