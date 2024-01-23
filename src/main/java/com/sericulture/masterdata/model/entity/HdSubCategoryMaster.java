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
public class HdSubCategoryMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hd_sub_category_master_seq")
    @SequenceGenerator(name = "hd_sub_category_master_seq", sequenceName = "hd_sub_category_master_seq", allocationSize = 1)
    @Column(name = "hd_sub_category_id")
    private Long hdSubCategoryId;

    @Size(min = 2, max = 250, message = "  Hd Sub Category Master name should be more than 1 characters.")
    @Column(name = "hd_sub_category_name", unique = true)
    private String hdSubCategoryName;
}
