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
public class TsBudgetHoa extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ts_budget_hoa_seq")
    @SequenceGenerator(name = "ts_budget_hoa_seq", sequenceName = "ts_budget_hoa_seq", allocationSize = 1)
    @Column(name = "ts_budget_hoa_id")
    private Long tsBudgetHoaId;

    @Column(name = "financial_year_master_id")
    private Long financialYearMasterId;

    @Column(name = "hao_id")
    private Long scHeadAccountId;

    @Column(name = "date")
    private Date date;

    @Column(name = "buget_amount")
    private BigDecimal budgetAmount;


}
