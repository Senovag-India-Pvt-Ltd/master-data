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
public class TrProgramMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tr_program_seq")
    @SequenceGenerator(name = "tr_program_seq", sequenceName = "tr_program_seq", allocationSize = 1)
    @Column(name = "tr_program_id")
    private Long trProgramMasterId;


    @Size(min = 2, max = 250, message = "Program name should be more than 1 characters.")
    @Column(name = " tr_program_name", unique = true)
    private String trProgramMasterName;

    @Column(name = " tr_program_name_in_kannada", unique = true)
    private String trProgramNameInKannada;
}
