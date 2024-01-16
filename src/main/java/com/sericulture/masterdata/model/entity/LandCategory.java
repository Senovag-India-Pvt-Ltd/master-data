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
public class LandCategory extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "land_category_seq")
    @SequenceGenerator(name = "land_category_seq", sequenceName = "land_category_seq", allocationSize = 1)
    @Column(name = "land_category_id")
    private Long id;


    @Size(min = 2, max = 250, message = "Land Category name should be more than 1 characters.")
    @Column(name = "land_category_name", unique = true)
    private String landCategoryName;

    @Column(name = "land_category_name_in_kannada")
    private String landCategoryNameInKannada;
}
