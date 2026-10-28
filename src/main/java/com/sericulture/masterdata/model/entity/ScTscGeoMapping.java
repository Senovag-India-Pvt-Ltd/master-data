package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class ScTscGeoMapping extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_tsc_geo_mapping_seq")
    @SequenceGenerator(name = "sc_tsc_geo_mapping_seq", sequenceName = "sc_tsc_geo_mapping_seq", allocationSize = 1)
    @Column(name = "sc_tsc_geo_mapping_id")
    private Long scTscGeoMappingId;

    @NotNull(message = "District is required.")
    @Column(name = "district_id")
    private Long districtId;

    @NotNull(message = "Taluk is required.")
    @Column(name = "taluk_id")
    private Long talukId;

    @Column(name = "hobli_id")
    private Long hobliId;

    @Column(name = "village_id")
    private Long villageId;

    @NotNull(message = "TSC is required.")
    @Column(name = "tsc_master_id")
    private Long tscMasterId;

    @Column(name = "officer_user_id")
    private Long officerUserId;
}
