package com.sericulture.masterdata.model.api.tsReleaseBudgetInstitution;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditTsReleaseBudgetInstitutionRequest extends RequestBody {
    @Schema(name = "tsReleaseBudgetInstitutionId", example = "1")
    Long tsReleaseBudgetInstitutionId;


    @Schema(name = "financialYearMasterId", example = "1")
    Long financialYearMasterId;

    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;


    @Schema(name="districtId", example = "1")
    Long districtId;

    @Schema(name = "talukId", example = "1")
    Long talukId;


    @Schema(name = "institutionType", example = "1")
    Long institutionType;


    @Schema(name = "institutionId", example = "1")
    Long institutionId;

    @Schema(name = "date", example = "1")
    Date date;

    @Schema(name = "budgetAmount", example = "1")
    BigDecimal budgetAmount;
}
