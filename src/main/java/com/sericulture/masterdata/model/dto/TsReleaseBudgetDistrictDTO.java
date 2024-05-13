package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsReleaseBudgetDistrictDTO {
    private Long tsReleaseBudgetDistrictId;
    private Long financialYearMasterId;
    private Long scHeadAccountId;
    private Long districtId;
    private Date date;
    private BigDecimal budgetAmount;
    private String financialYear;
    private String districtName;
    private String scHeadAccountName;
}
