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
public class RendittaMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "renditta_master_seq")
    @SequenceGenerator(name = "renditta_master_seq", sequenceName = "renditta_master_seq", allocationSize = 1)
    @Column(name = "renditta_master_id")
    private Long rendittaMasterId;

    @Column(name = "race_master_id")
    private Long raceMasterId;

    @Column(name = "value")
    private BigDecimal value;

}
