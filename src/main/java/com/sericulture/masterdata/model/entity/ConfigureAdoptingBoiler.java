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
public class ConfigureAdoptingBoiler  extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configure_silk_incentive_seq")
    @SequenceGenerator(name = "configure_silk_incentive_seq", sequenceName = "configure_silk_incentive_seq", allocationSize = 1)
    @Column(name = "adopting_boiler_id")
    private Long adoptingBoilerId;

    @Column(name = "boiler_in_kg")
    private Float boilerInKg;

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
