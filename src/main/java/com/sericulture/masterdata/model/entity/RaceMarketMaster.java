package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
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


public class RaceMarketMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RACE_MARKET_MASTER_seq")
    @SequenceGenerator(name = "RACE_MARKET_MASTER_seq", sequenceName = "RACE_MARKET_MASTER_seq", allocationSize = 1)
    @Column(name = "RACE_MARKET_MASTER_ID")
    private Long raceMarketMasterId;

    @Column(name = "MARKET_MASTER_ID")
    private Long marketMasterId;

    @Column(name = "RACE_ID")
    private Long raceMasterId;

}
