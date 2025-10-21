package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfigureIcb extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configure_icb_seq")
    @SequenceGenerator(name = "configure_icb_seq", sequenceName = "configure_icb_seq", allocationSize = 1)
    @Column(name = "icb_id")
    private Long icbId;

    @Column(name = "icb_basin_ends")
    private String icbBasinEnds;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "component_id")
    private Long componentId;

    @Column(name = "component_type_id")
    private Long componentTypeId;

    @Column(name = "unit_cost")
    private Float unitCost;

    @Column(name = "min")
    private Float min;

    @Column(name = "max")
    private Float max;
}

