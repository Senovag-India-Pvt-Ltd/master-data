package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsBudgetHoaDTO {
    private Long tsBudgetHoaId;
    private Long financialYearMasterId;
    private Long scHeadAccountId;
    private Date date;
    private BigDecimal budgetAmount;
    private String financialYear;
    private String scHeadAccountName;
}
