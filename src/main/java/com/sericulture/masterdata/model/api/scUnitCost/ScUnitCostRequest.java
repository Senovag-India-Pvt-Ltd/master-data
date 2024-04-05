package com.sericulture.masterdata.model.api.scUnitCost;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScUnitCostRequest extends RequestBody {
    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;

    @Schema(name = "scCategoryId", example = "1")
    Long scCategoryId;

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Long scSubSchemeDetailsId;

    @Schema(name = "centralShare", example = "1")
    BigDecimal centralShare;

    @Schema(name = "stateShare", example = "1")
    BigDecimal stateShare;

    @Schema(name = "benificiaryShare", example = "1")
    BigDecimal benificiaryShare;

    @Schema(name = "unitCost", example = "1")
    BigDecimal unitCost;
}