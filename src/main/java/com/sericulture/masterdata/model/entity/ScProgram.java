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
public class ScProgram extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_program_seq")
    @SequenceGenerator(name = "sc_program_seq", sequenceName = "sc_program_seq", allocationSize = 1)
    @Column(name = "sc_program_id")
    private Long scProgramId;


    @Size(min = 2, max = 250, message = "Sc Program name should be more than 1 characters.")
    @Column(name = "sc_program_name", unique = true)
    private String scProgramName;
}
