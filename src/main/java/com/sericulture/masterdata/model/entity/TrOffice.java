package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TrOffice extends  BaseEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TR_OFFICE_SEQ")
    @SequenceGenerator(name = "TR_OFFICE_SEQ", sequenceName = "TR_OFFICE_SEQ", allocationSize = 1)
    @Column(name = "TR_OFFICE_ID")
    private Long trOfficeId;


    @Size(min = 2, max = 250, message = "Tr Office name should be more than 1 characters.")
    @Column(name = "TR_OFFICE_NAME", unique = true)
    private String trOfficeName;
}
