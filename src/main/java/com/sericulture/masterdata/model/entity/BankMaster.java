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
public class BankMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_master_seq")
    @SequenceGenerator(name = "bank_master_seq", sequenceName = "bank_master_seq", allocationSize = 1)
    @Column(name = "bank_master_id")
    private Long bankMasterId;


    @Size(min = 2, max = 250, message = "Bank name should be more than 1 characters.")
    @Column(name = " bank_name", unique = true)
    private String bankName;

    @Column(name = " bank_name_in_kannada", unique = true)
    private String bankNameInKannada;
}
