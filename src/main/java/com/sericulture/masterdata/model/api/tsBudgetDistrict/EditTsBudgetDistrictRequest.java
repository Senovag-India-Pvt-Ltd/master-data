package com.sericulture.masterdata.model.api.tsBudgetDistrict;

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
public class EditTsBudgetDistrictRequest extends RequestBody {

    @Schema(name = "tsBudgetDistrictId", example = "1")
    Long tsBudgetDistrictId;


    @Schema(name = "financialYearMasterId", example = "1")
    Long financialYearMasterId;

    @Schema(name = "hoaId", example = "1")
    Long hoaId;

    @Schema(name = "date", example = "1")
    Date date;

    @Schema(name = "budgetAmount", example = "1")
    BigDecimal budgetAmount;


    @Schema(name="districtId", example = "1")
    int districtId;


}
