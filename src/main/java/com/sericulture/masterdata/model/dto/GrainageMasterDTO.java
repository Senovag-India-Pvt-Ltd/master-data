package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrainageMasterDTO {

    private Long grainageMasterId;
    private String grainageMasterName;
    private String grainageMasterNameInKannada;
    private String grainageNameRepresentation;
    private String grainageType;
    private Long userMasterId;
    private String username;

}
