package com.sericulture.masterdata.model.api.dbtCodeFinancialYear;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DbtCodeFinancialYearResponse {

    @Schema(name = "scDbtCodeFinancialYearId", example = "1")
    Long scDbtCodeFinancialYearId;

    @Schema(name = "masterType", example = "SCHEME_QUOTA")
    String masterType;

    @Schema(name = "parentId", example = "1")
    Long parentId;

    @Schema(name = "financialYearMasterId", example = "1")
    Long financialYearMasterId;

    @Schema(name = "financialYear", example = "2026-2027")
    String financialYear;

    @Schema(name = "dbtCode", example = "90")
    String dbtCode;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "DBT code already configured for this Financial Year")
    String error_description;
}
