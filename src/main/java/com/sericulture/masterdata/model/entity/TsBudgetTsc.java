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
public class TsBudgetTsc extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ts_budget_tsc_seq")
    @SequenceGenerator(name = "ts_budget_tsc_seq", sequenceName = "ts_budget_tsc_seq", allocationSize = 1)
    @Column(name = "ts_budget_tsc_id")
    private Long tsBudgetTscId;

    @Column(name = "financial_year_master_id")
    private Long financialYearMasterId;

    @Column(name = "hao_id")
    private Long hoaId;

    @Column(name = "date")
    private Date date;

    @Column(name = "buget_amount")
    private BigDecimal budgetAmount;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "taluk_id")
    private Long talukId;

    @Column(name = "tsc_master_id")
    private Long tscMasterId;
}
