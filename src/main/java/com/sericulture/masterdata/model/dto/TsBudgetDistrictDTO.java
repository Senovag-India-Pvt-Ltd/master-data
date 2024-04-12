package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsBudgetDistrictDTO {

    private Long tsBudgetDistrictId;
    private Long financialYearMasterId;
    private Long hoaId;
    private Date date;
    private BigDecimal budgetAmount;
    private Integer districtId;
    private String financialYear;
    private String districtName;
}
