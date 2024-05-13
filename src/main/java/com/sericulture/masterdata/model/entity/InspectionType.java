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
public class InspectionType extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_type_seq")
    @SequenceGenerator(name = "inspection_type_seq", sequenceName = "inspection_type_seq", allocationSize = 1)
    @Column(name = "inspection_type_id")
    private Long inspectionTypeId;


    @Size(min = 2, max = 250, message = " name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "name_in_kannada")
    private String nameInKannada;

    @Column(name = "value")
    private Long value;

    @Column(name = "version")
    private Long version;
}
