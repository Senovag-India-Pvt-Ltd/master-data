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
public class FinancialYearMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_year_master_seq")
    @SequenceGenerator(name = "financial_year_master_seq", sequenceName = "financial_year_master_seq", allocationSize = 1)
    @Column(name = "financial_year_master_id")
    private Long financialYearMasterId;


    @Size(min = 2, max = 250, message = "Financial Year should be more than 1 characters.")
    @Column(name = " financial_year", unique = true)
    private String financialYear;

    @Column(name = " is_default", unique = true)
    private Long isDefault;
}
