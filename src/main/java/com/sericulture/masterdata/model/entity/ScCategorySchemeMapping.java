package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "sc_category_scheme_mapping")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScCategorySchemeMapping extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_category_scheme_mapping_seq")
    @SequenceGenerator(name = "sc_category_scheme_mapping_seq",
            sequenceName = "sc_category_scheme_mapping_seq", allocationSize = 1)
    @Column(name = "sc_category_mapping_id")
    private Long scCategoryMappingId;

    @Column(name = "sc_category_id")
    private Long scCategoryId;

    @Column(name = "scheme_id")
    private Long schemeId;

    @Column(name = "sub_scheme_id")
    private Long subSchemeId;

    @Column(name = "dbt_code")
    private String dbtCode;
}
