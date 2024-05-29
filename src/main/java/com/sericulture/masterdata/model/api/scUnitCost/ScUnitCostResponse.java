package com.sericulture.masterdata.model.api.scUnitCost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScUnitCostResponse {
    @Schema(name = "scUnitCostId", example = "1")
    Long scUnitCostId;


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

//    @Schema(name = "unitCost", example = "1")
//    BigDecimal unitCost;

    @Schema(name = "scHeadAccountName", example = "Karnataka", required = true)
    String scHeadAccountName;

    @Schema(name = "categoryName", example = "Karnataka", required = true)
    String categoryName;

    @Schema(name = "subSchemeName", example = "Karnataka", required = true)
    String subSchemeName;


    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
