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
public class GrainageMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grainage_master_seq")
    @SequenceGenerator(name = "grainage_master_seq", sequenceName = "grainage_master_seq", allocationSize = 1)
    @Column(name = "grainage_master_id")
    private Long grainageMasterId;


    @Size(min = 2, max = 250, message = "grainage name should be more than 1 characters.")
    @Column(name = "grainage_master_name", unique = true)
    private String grainageMasterName;

    @Column(name = "grainage_master_name_in_kannada", unique = true)
    private String grainageMasterNameInKannada;

    @Column(name = "user_master_id")
    private Long userMasterId;
}

