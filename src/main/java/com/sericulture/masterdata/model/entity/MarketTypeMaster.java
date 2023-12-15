package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
public class MarketTypeMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "market_type_master_seq")
    @SequenceGenerator(name = "market_type_master_seq", sequenceName = "market_type_master_seq", allocationSize = 1)
    @Column(name = "market_type_master_id")
    private Long marketTypeMasterId;

    @Size(min = 2, max = 250, message = "Market Type name should be more than 1 characters.")
    @Column(name = "market_type_master_name", unique = true)
    private String marketTypeMasterName;

    @Column(name = "reeler_fee")
    private BigDecimal reelerFee;

    @Column(name = "farmer_fee")
    private BigDecimal farmerFee;

    @Column(name = "trader_fee")
    private BigDecimal traderFee;
}
