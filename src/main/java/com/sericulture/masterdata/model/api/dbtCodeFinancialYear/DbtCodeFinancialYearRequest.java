package com.sericulture.masterdata.model.api.dbtCodeFinancialYear;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DbtCodeFinancialYearRequest extends RequestBody {

    @Schema(name = "masterType", example = "SCHEME_QUOTA", required = true,
            description = "SC_COMPONENT | SC_SUB_SCHEME_DETAILS | SCHEME_QUOTA | SC_CATEGORY_SCHEME_MAPPING | SC_CATEGORY | SC_SCHEME_DETAILS")
    String masterType;

    @Schema(name = "parentId", example = "1", required = true, description = "Primary key of the scheme/component/sub-scheme/quota/category-mapping row this code belongs to")
    Long parentId;

    @Schema(name = "financialYearMasterId", example = "1", required = true)
    Long financialYearMasterId;

    @Schema(name = "dbtCode", example = "90", required = true)
    String dbtCode;
}
