package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScUnitCostDTO {
    private Long scUnitCostId;
    private Long scHeadAccountId;
    private Long scCategoryId;
    private Long scSubSchemeDetailsId;
    private BigDecimal centralShare;
    private BigDecimal stateShare;
    private BigDecimal benificiaryShare;
    private String scHeadAccountName;
    private String categoryName;
    private String subSchemeName;

}
