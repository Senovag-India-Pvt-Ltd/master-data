package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScSchemeDetails extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_scheme_details_seq")
    @SequenceGenerator(name = "sc_scheme_details_seq", sequenceName = "sc_scheme_details_seq", allocationSize = 1)
    @Column(name = "sc_scheme_details_id")
    private Long scSchemeDetailsId;


    @Size(min = 2, max = 250, message = "Program name should be more than 1 characters.")
    @Column(name = " scheme_name", unique = true)
    private String schemeName;

    @Column(name = "scheme_name_in_kannada", unique = true)
    private String schemeNameInKannada;

    @Column(name = "scheme_start_date", unique = true)
    private Date schemeStartDate;

    @Column(name = "scheme_end_date", unique = true)
    private Date schemeEndDate;

    @Column(name = "dbt_code", unique = true)
    private String dbtCode;

    @Column(name = "hectare" ,columnDefinition = "TINYINT")
    private Boolean hectare;

    @Column(name = "spacing" ,columnDefinition = "TINYINT")
    private Boolean spacing;
}
