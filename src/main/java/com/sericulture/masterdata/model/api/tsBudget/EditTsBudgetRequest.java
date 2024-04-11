package com.sericulture.masterdata.model.api.tsBudget;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditTsBudgetRequest extends RequestBody {

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


}
