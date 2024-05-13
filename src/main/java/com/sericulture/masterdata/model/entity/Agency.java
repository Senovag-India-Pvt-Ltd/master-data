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
public class Agency extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agency_seq")
    @SequenceGenerator(name = "agency_seq", sequenceName = "agency_seq", allocationSize = 1)
    @Column(name = "agency_id")
    private Long agencyId;

    @Column(name = "agency_code")
    private String agencyCode;

    @Column(name = "agency_bank_acc_no")
    private String agencyBankAcNo;

    @Column(name = "agency_ifsc_code")
    private String agencyIfscCode;

    @Column(name = "agency_district_code")
    private String agencyDistrictCode;

    @Column(name = "agency_taluk_code")
    private String agencyTalukCode;

}
