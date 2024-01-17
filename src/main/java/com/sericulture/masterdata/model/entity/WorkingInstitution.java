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
public class WorkingInstitution extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "working_institution_seq")
    @SequenceGenerator(name = "working_institution_seq", sequenceName = "working_institution_seq", allocationSize = 1)
    @Column(name = "working_institution_id")
    private Long workingInstitutionId;

    @Size(min = 2, max = 250, message = "Working Institution name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String workingInstitutionName;

    @Column(name = "working_institution_name_in_kannada")
    private String workingInstitutionNameInKannada;

}
