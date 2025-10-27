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
public class ScSubSchemeDetails extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_sub_scheme_details_seq")
    @SequenceGenerator(name = "sc_sub_scheme_details_seq", sequenceName = "sc_sub_scheme_details_seq", allocationSize = 1)
    @Column(name = "sc_sub_scheme_details_id")
    private Long scSubSchemeDetailsId;

    @Column(name = "sc_scheme_details_id")
    private Long scSchemeDetailsId;


    @Size(min = 2, max = 250, message = "Sub Scheme Name should be more than 1 characters.")
    @Column(name = "sub_scheme_name", unique = true)
    private String subSchemeName;


    @Column(name = "sub_scheme_name_in_kannada", unique = true)
    private String subSchemeNameInKannada;

    @Column(name = "sub_scheme_type")
    private Long subSchemeType;

    @Column(name = "sub_scheme_start_date", unique = true)
    private Date subSchemeStartDate;

    @Column(name = "sub_scheme_end_date", unique = true)
    private Date subSchemeEndDate;

    @Column(name = "with_land" ,columnDefinition = "TINYINT")
    private Boolean withLand;

    @Column(name = "beneficiary_type")
    private Long beneficiaryType;

    @Column(name = "dbt_code", unique = true)
    private String dbtCode;

    @Column(name = "allow_multiple_sanction" ,columnDefinition = "TINYINT")
    private Boolean allowMultipleSanction;

    @Column(name = "sanction_for_reeling" ,columnDefinition = "TINYINT")
    private Boolean sanctionForReeling;

    @Column(name = "calculation_based_on")
    private String calculationBasedOn;

    @Column(name = "work_order_for_scheme")
    private String workOrderForScheme;

    @Column(name = "sanction_order_for_scheme")
    private String sanctionOrderForScheme;

    @Column(name = "unit_for_scheme")
    private String unitForScheme;

    @Column(name = "acknowledgement_for_scheme")
    private String acknowledgementForScheme;

//    @Column(name = "sanction_order_for_scheme")
//    private String sanctionOrderForScheme;

}
