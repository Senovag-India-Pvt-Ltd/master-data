package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name="bin_master")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BinMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BIN_MASTER_SEQ")
    @SequenceGenerator(name = "BIN_MASTER_SEQ", sequenceName = "BIN_MASTER_SEQ", allocationSize = 1)
    @Column(name = "BIN_MASTER_ID")
    private int binMasterId;

    @Column(name = "BIN_COUNTER_MASTER_ID")
    private Integer binCounterMasterId;

    @Column(name = "MARKET_ID")
    private int marketId;

    @Column(name = "GODOWN_ID")
    private int godownId;

    @Column(name = "BIN_NUMBER")
    private int binNumber;


    @Size(message = "small or big ")
    @Column(name = "BIN_TYPE")
    private String type;

    @Column(name = "BIN_STATUS")
    @Size(message = "available or not")
    private String status;

}
