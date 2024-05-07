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
public class LineNameMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "line_name_master_seq")
    @SequenceGenerator(name = "line_name_master_seq", sequenceName = "line_name_master_seq", allocationSize = 1)
    @Column(name = "line_name_id")
    private Long lineNameId;


    @Size(min = 2, max = 250, message = "Line Name Master name should be more than 1 characters.")
    @Column(name = "line_name", unique = true)
    private String lineName;

    @Column(name = "line_name_representation", unique = true)
    private String lineNameRepresentation;

    @Column(name = "line_code")
    private String lineCode;

    @Column(name = "line_name_in_kannada", unique = true)
    private String lineNameInKannada;
}
