package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrateMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRATE_MASTER_seq")
    @SequenceGenerator(name = "CRATE_MASTER_seq", sequenceName = "CRATE_MASTER_seq", allocationSize = 1)
    @Column(name = "CRATE_MASTER_ID")
    private Long crateMasterId;

    @Column(name = "RACE_MASTER_ID")
    private Long raceMasterId;

    @Column(name = "MARKET_ID")
    private Long marketId;

    @Column(name = "GODOWN_ID")
    private Long godownId;

    @Column(name = "APPROX_WEIGHT_PER_CRATE")
    private Long approxWeightPerCrate;

}
