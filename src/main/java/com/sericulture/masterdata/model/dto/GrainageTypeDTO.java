package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrainageTypeDTO {

    private Long grainageTypeId;
    private Long grainageMasterId;
    private String name;
    private String nameInKannada;
    private String grainageMasterName;


}
