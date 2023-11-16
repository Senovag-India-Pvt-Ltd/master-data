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
public class VendorMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_seq")
    @SequenceGenerator(name = "vendor_seq", sequenceName = "vendor_seq", allocationSize = 1)
    @Column(name = "vendor_id")
    private Long vendorMasterId;


    @Size(min = 2, max = 250, message = "Vendor name should be more than 1 characters.")
    @Column(name = "vendor_name", unique = true)
    private String vendorMasterName;
}
