package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TsBudget  extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ts_budget_seq")
    @SequenceGenerator(name = "ts_budget_seq", sequenceName = "ts_budget_seq", allocationSize = 1)
    @Column(name = "ts_budget_id")
    private Long tsBudgetId;

    @Column(name = "financial_master_id")
    private Long financialYearMasterId;

    @Column(name = "date")
    private Date date;

    @Column(name = "central_budget")
    private BigDecimal centralBudget;

    @Column(name = "state_budget")
    private BigDecimal stateBudget;

    @Column(name = "amount")
    private BigDecimal amount;


}
