package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsBudgetTscDTO {
    private Long tsBudgetTscId;
    private Long financialYearMasterId;
    private Long scHeadAccountId;
    private Date date;
    private BigDecimal budgetAmount;
    private Long districtId;
    private Long talukId;
    private Long tscMasterId;
    private String financialYear;
    private String districtName;
    private String talukName;
    private String name;
    private String scHeadAccountName;

}
