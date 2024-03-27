package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScUnitCost extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_unit_cost_seq")
    @SequenceGenerator(name = "sc_unit_cost_seq", sequenceName = "sc_unit_cost_seq", allocationSize = 1)
    @Column(name = "sc_unit_cost_id")
    private Long scUnitCostId;

    @Column(name = "sc_head_account_id")
    private Long scHeadAccountId;

    @Column(name = "sc_category_id")
    private Long scCategoryId;


    @Column(name = "sc_sub_scheme_details_id")
    private Long scSubSchemeDetailsId;

    @Column(name = "central_share")
    private BigDecimal centralShare;

    @Column(name = "state_share")
    private BigDecimal stateShare;


    @Column(name = "benificiary_share")
    private BigDecimal benificiaryShare;
}
