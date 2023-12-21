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
public class BinCounterMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bin_counter_master_seq")
    @SequenceGenerator(name = "bin_counter_master_seq", sequenceName = "bin_counter_master_seq", allocationSize = 1)
    @Column(name = "bin_counter_master_id")
    private int binCounterMasterId;

    @Column(name = "market_Id")
    private Long marketId;

    @Column(name = "godown_id")
    private Long godownId;

    @Column(name = "small_bin_start")
    private Long smallBinStart;

    @Column(name = "small_bin_end")
    private Long smallBinEnd;

    @Column(name = "big_bin_start")
    private Long bigBinStart;

    @Column(name = "big_bin_end")
    private Long bigBinEnd;
}
