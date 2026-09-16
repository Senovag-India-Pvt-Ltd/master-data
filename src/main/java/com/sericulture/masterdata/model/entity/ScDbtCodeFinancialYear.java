package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "sc_dbt_code_financial_year")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScDbtCodeFinancialYear extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_dbt_code_financial_year_seq")
    @SequenceGenerator(name = "sc_dbt_code_financial_year_seq", sequenceName = "sc_dbt_code_financial_year_seq", allocationSize = 1)
    @Column(name = "sc_dbt_code_financial_year_id")
    private Long scDbtCodeFinancialYearId;

    @NotBlank(message = "Master type is mandatory.")
    @Column(name = "master_type")
    private String masterType;

    @NotNull(message = "Parent id is mandatory.")
    @Column(name = "parent_id")
    private Long parentId;

    @NotNull(message = "Financial year is mandatory.")
    @Column(name = "financial_year_master_id")
    private Long financialYearMasterId;

    @NotBlank(message = "DBT Code is mandatory.")
    @Column(name = "dbt_code")
    private String dbtCode;
}
