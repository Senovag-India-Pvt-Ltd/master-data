package com.sericulture.masterdata.model.api.dbtCodeFinancialYear;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditDbtCodeFinancialYearRequest extends RequestBody {

    @Schema(name = "scDbtCodeFinancialYearId", example = "1", required = true)
    Long scDbtCodeFinancialYearId;

    @Schema(name = "masterType", example = "SCHEME_QUOTA", required = true)
    String masterType;

    @Schema(name = "parentId", example = "1", required = true)
    Long parentId;

    @Schema(name = "financialYearMasterId", example = "1", required = true)
    Long financialYearMasterId;

    @Schema(name = "dbtCode", example = "90", required = true)
    String dbtCode;
}
