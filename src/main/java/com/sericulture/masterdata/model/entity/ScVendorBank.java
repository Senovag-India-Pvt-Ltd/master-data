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
public class ScVendorBank extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_vendor_bank_seq")
    @SequenceGenerator(name = "sc_vendor_bank_seq", sequenceName = "sc_vendor_bank_seq", allocationSize = 1)
    @Column(name = "sc_vendor_bank_id")
    private Long scVendorBankId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "branch")
    private String branch;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "upi")
    private String upi;

    @Column(name = "sc_vendor_id")
    private Long scVendorId;
}
