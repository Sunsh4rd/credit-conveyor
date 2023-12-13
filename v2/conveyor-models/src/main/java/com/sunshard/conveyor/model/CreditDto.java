package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.CreditStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "Calculated credit data")
public class CreditDto {

    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "credit id"
    )
    private Long id;

    @Schema(
            type = "number",
            example = "300000",
            description = "amount of your loan"
    )
    @NotNull
    @Min(10000)
    private BigDecimal amount;

    @Schema(
            type = "integer",
            example = "18",
            description = "term of your loan"
    )
    @Min(6)
    @NotNull
    private Integer term;

    @Schema(
            type = "number",
            example = "18047.55",
            description = "monthly payment amount"
    )
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(
            type = "number",
            example = "12.50",
            description = "loan rate"
    )
    @NotNull
    private BigDecimal rate;

    @Schema(
            type = "number",
            example = "448000",
            description = "full loan price"
    )
    @NotNull
    private BigDecimal psk;

    @Schema(
            type = "boolean",
            example = "true",
            description = "shows whether insurance is enabled"
    )
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(
            type = "boolean",
            example = "true",
            description = "shows whether loaner is a salary client"
    )
    @NotNull
    private Boolean isSalaryClient;

    @Schema(
            description = "list-organized payment schedule data"
    )
    @NotEmpty
    private List<PaymentScheduleElement> paymentSchedule;

    @Schema(
            type = "enum",
            example = "CALCULATED",
            description = "status of the credit"
    )
    @NotNull
    private CreditStatus creditStatus;
}
