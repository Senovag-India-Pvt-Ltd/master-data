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
public class FarmMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disinfectant_master_seq")
    @SequenceGenerator(name = "disinfectant_master_seq", sequenceName = "disinfectant_master_seq", allocationSize = 1)
    @Column(name = "farm_id")
    private Long farmId;


    @Size(min = 2, max = 250, message = "Farm name should be more than 1 characters.")
    @Column(name = "farm_name", unique = true)
    private String farmName;

    @Column(name = "farm_name_in_kannada", unique = true)
    private String farmNameInKannada;

    @Column(name = "user_master_id")
    private Long userMasterId;

    @Column(name = "is_bsf")
    private String isBsf;
}
