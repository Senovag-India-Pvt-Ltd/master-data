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
public class ConfigureReelingShed extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configure_reeling_shed_seq")
    @SequenceGenerator(name = "configure_reeling_shed_seq", sequenceName = "configure_reeling_shed_seq", allocationSize = 1)
    @Column(name = "reeling_shed_id")
    private Long reelingShedId;

    @Column(name = "reeling_unit")
    private String reelingUnit;

    @Column(name = "sqft")
    private String sqft;

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


