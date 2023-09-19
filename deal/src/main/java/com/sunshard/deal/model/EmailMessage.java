package com.sunshard.deal.model;

import com.sunshard.deal.model.enums.Theme;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Email message data for kafka")
public class EmailMessage {

    @Schema(
            type = "string",
            example = "your@mail.com",
            description = "your email"
    )
    private String address;

    @Schema(
            type = "enum",
            example = "FINISH_REGISTRATION",
            description = "Theme of the email"
    )
    private Theme theme;

    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "application id"
    )
    private Long applicationId;
}
