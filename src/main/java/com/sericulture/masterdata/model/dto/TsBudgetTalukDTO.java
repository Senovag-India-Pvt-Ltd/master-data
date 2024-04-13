package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsBudgetTalukDTO {
    private Long tsBudgetTalukId;
    private Long financialYearMasterId;
    private Long hoaId;
    private Date date;
    private BigDecimal budgetAmount;
    private Long districtId;
    private Long talukId;
    private String financialYear;
    private String districtName;
    private String talukName;

}
