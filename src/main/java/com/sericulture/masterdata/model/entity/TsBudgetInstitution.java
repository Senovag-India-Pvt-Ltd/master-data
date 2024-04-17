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
public class TsBudgetInstitution extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ts_budget_institution_seq")
    @SequenceGenerator(name = "ts_budget_institution_seq", sequenceName = "ts_budget_institution_seq", allocationSize = 1)
    @Column(name = "ts_budget_institution_id")
    private Long tsBudgetInstitutionId;

    @Column(name = "financial_year_master_id")
    private Long financialYearMasterId;

    @Column(name = "hao_id")
    private Long scHeadAccountId;


    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "taluk_id")
    private Long talukId;

    @Column(name = "institution_type")
    private Long institutionType;

    @Column(name = "institution_id")
    private Long institutionId;

    @Column(name = "date")
    private Date date;

    @Column(name = "buget_amount")
    private BigDecimal budgetAmount;

}
