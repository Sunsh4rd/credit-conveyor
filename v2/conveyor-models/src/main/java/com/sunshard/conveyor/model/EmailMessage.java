package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.Theme;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Email message data for kafka")
public class EmailMessage {

    @Schema(
            type = "string",
            example = "your@mail.com",
            description = "your email"
    )
    @NotBlank
    private String address;

    @Schema(
            type = "enum",
            example = "FINISH_REGISTRATION",
            description = "Theme of the email"
    )
    @NotNull
    private Theme theme;

    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "application id"
    )
    @NotNull
    private Long applicationId;
}
