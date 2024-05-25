package com.sericulture.masterdata.model.api.tsBudget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TsBudgetResponse {
    @Schema(name = "tsBudgetId", example = "1")
    Long tsBudgetId;


    @Schema(name = "financialYearMasterId", example = "1")
    Long financialYearMasterId;

    @Schema(name = "date", example = "1")
    Date date;

    @Schema(name = "centralBudget", example = "1")
    BigDecimal centralBudget;

    @Schema(name = "stateBudget", example = "1")
    BigDecimal stateBudget;

    @Schema(name = "amount", example = "1")
    BigDecimal amount;

    @Schema(name = "financialYear", example = "Karnataka", required = true)
    String financialYear;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
