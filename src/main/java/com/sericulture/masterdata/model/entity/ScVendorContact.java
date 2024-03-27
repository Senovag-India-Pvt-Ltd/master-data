package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
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
public class ScVendorContact extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_vendor_contact_seq")
    @SequenceGenerator(name = "sc_vendor_contact_seq", sequenceName = "sc_vendor_contact_seq", allocationSize = 1)
    @Column(name = "sc_vendor_contact_id")
    private Long scVendorContactId;

    @Column(name = "vendor_address")
    private String vendorAddress;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "sc_vendor_id")
    private Long scVendorId;
}
