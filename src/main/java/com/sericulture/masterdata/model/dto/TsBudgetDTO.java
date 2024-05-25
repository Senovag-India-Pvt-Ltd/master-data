package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsBudgetDTO {

    private Long tsBudgetId;
    private Long financialYearMasterId;
    private Date date;
    private BigDecimal centralBudget;
    private BigDecimal stateBudget;
    private BigDecimal amount;
    private String financialYear;
}
