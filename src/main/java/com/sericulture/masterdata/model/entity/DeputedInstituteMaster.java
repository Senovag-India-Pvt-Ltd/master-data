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
public class DeputedInstituteMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debuted_institute_master_seq")
    @SequenceGenerator(name = "debuted_institute_master_seq", sequenceName = "debuted_institute_master_seq", allocationSize = 1)
    @Column(name = "deputed_institute_id")
    private Long deputedInstituteId;


    @Size(min = 2, max = 250, message = "Deputed Institute name should be more than 1 characters.")
    @Column(name = "deputed_institute_name", unique = true)
    private String deputedInstituteName;

}
