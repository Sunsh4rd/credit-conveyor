package com.sunshard.dossier.model;

import com.sunshard.dossier.model.enums.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EmailMessage {
    private String address;
    private Theme theme;
    private Long applicationId;
}
