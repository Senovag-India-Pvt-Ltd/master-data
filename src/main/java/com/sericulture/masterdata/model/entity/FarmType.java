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
public class FarmType extends  BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farm_type_seq")
    @SequenceGenerator(name = "farm_type_seq", sequenceName = "farm_type_seq", allocationSize = 1)
    @Column(name = "farm_type_id")
    private Long farmTypeId;

    @Column(name = "farm_master_id")
    private Long farmId;

    @Size(min = 2, max = 250, message = "grainage name should be more than 1 characters.")
    @Column(name = "name")
    private String name;

    @Column(name = "name_in_kannada")
    private String nameInKannada;

}
