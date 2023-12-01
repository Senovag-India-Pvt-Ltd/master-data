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
public class Designation extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESIGNATION_SEQ")
    @SequenceGenerator(name = "DESIGNATION_SEQ", sequenceName = "DESIGNATION_SEQ", allocationSize = 1)
    @Column(name = "DESIGNATION_ID")
    private Long designationId;


    @Size(min = 2, max = 250, message = "Name should be more than 1 characters.")
    @Column(name = "name")
    private String name;

}
