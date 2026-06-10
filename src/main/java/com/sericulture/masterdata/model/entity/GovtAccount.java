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
public class GovtAccount extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "govt_account_seq")
    @SequenceGenerator(name = "govt_account_seq", sequenceName = "govt_account_seq", allocationSize = 1)
    @Column(name = "govt_account_id")
    private Long govtAccountId;

    @Size(min = 1, max = 50, message = "Govt account number should be between 1 and 50 characters.")
    @Column(name = "govt_account_number", unique = true)
    private String govtAccountNumber;

    @Size(min = 2, max = 250, message = "Bank name should be between 2 and 250 characters.")
    @Column(name = "bank_name")
    private String bankName;

    @Size(min = 2, max = 250, message = "Branch should be between 2 and 250 characters.")
    @Column(name = "branch")
    private String branch;

    @Size(min = 11, max = 11, message = "IFSC code must be exactly 11 characters.")
    @Column(name = "ifsc_code")
    private String ifscCode;
}