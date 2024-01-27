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
public class HdFeatureMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hd_feature_master_seq")
    @SequenceGenerator(name = "hd_feature_master_seq", sequenceName = "hd_feature_master_seq", allocationSize = 1)
    @Column(name = "hd_feature_id")
    private Long hdFeatureId;

    @Column(name = "hd_module_id")
    private Long hdModuleId;


    @Size(min = 2, max = 250, message = "Feature should be more than 1 characters.")
    @Column(name = "hd_feature_name", unique = true)
    private String hdFeatureName;

}