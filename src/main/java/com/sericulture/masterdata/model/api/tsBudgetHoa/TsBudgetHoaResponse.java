package com.sericulture.masterdata.model.api.tsBudgetHoa;

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
public class TsBudgetHoaResponse {
    @Schema(name = "tsBudgetHoaId", example = "1")
    Long tsBudgetHoaId;


    @Schema(name = "financialYearMasterId", example = "1")
    Long financialYearMasterId;

    @Schema(name = "hoaId", example = "1")
    Long hoaId;

    @Schema(name = "date", example = "1")
    Date date;

    @Schema(name = "budgetAmount", example = "1")
    BigDecimal budgetAmount;

    @Schema(name = "financialYear", example = "Karnataka", required = true)
    String financialYear;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
