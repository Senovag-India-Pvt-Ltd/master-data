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
public class GrainageType extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grainage_type_seq")
    @SequenceGenerator(name = "grainage_type_seq", sequenceName = "grainage_type_seq", allocationSize = 1)
    @Column(name = "grainage_type_id")
    private Long grainageTypeId;

    @Column(name = "grainage_master_id")
    private Long grainageMasterId;

    @Size(min = 2, max = 250, message = "grainage name should be more than 1 characters.")
    @Column(name = "name")
    private String name;

    @Column(name = "name_in_kannada")
    private String nameInKannada;
}
