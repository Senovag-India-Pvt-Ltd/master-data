package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsReleaseBudgetTalukDTO {
    private Long tsReleaseBudgetTalukId;
    private Long financialYearMasterId;
    private Long scHeadAccountId;
    private Long districtId;
    private Long talukId;
    private Date date;
    private BigDecimal budgetAmount;
    private String financialYear;
    private String districtName;
    private String talukName;
    private String scHeadAccountName;


}
