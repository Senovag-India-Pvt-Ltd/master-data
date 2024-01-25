package com.sericulture.masterdata.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HdFeatureMasterDTO {
    private Long  hdFeatureId;
    private Long hdModuleId;
    private String hdFeatureName;
    private String hdModuleName;

}
