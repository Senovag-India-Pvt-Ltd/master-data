package com.sericulture.masterdata.model.api.financialYearMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialYearMasterResponse {
    @Schema(name = "financialYearMasterId", example = "1")
    Long financialYearMasterId;

    @Schema(name = "financialYear", example = "Karnataka", required = true)
    String financialYear;

    @Schema(name = "isDefault", example = "1")
    Long isDefault;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
