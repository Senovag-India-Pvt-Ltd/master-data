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
public class ScVendor extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_vendor_seq")
    @SequenceGenerator(name = "sc_vendor_seq", sequenceName = "sc_vendor_seq", allocationSize = 1)
    @Column(name = "sc_vendor_id")
    private Long scVendorId;


    @Size(min = 2, max = 250, message = "Vendor Name should be more than 1 characters.")
    @Column(name = " name", unique = true)
    private String name;

    @Column(name = "name_in_kannada", unique = true)
    private String nameInKannada;

    @Column(name = "type")
    private Long type;
}
