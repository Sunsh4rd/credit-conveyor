package com.sunshard.conveyor.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    @Schema(
            type = "integer",
            description = "Error code",
            example = "404"
    )
    private Integer errorCode;
    @Schema(
            type = "string",
            description = "Error message",
            example = "Not found"
    )
    private String errorMessage;

}
