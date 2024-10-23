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
public class FarmerBankAccountReason extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_year_master_seq")
    @SequenceGenerator(name = "financial_year_master_seq", sequenceName = "financial_year_master_seq", allocationSize = 1)
    @Column(name = "farmer_bank_account_reason_id")
    private Long farmerBankAccountReasonId;

    @Column(name = " farmer_bank_account_reason", unique = true)
    private String farmerBankAccountReason;

}
