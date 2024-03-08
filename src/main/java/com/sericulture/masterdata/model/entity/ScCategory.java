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
public class ScCategory extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_category_seq")
    @SequenceGenerator(name = "sc_category_seq", sequenceName = "sc_category_seq", allocationSize = 1)
    @Column(name = "sc_category_id")
    private Long scCategoryId;


    @Size(min = 2, max = 250, message = "Category Number should be more than 1 characters.")
    @Column(name = " category_number", unique = true)
    private String categoryNumber;

    @Column(name = " category_name", unique = true)
    private String categoryName;

    @Column(name = " category_name_in_kannada", unique = true)
    private String categoryNameInKannada;
}
