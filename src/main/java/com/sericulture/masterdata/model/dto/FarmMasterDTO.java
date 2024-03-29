package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmMasterDTO {

    private Long farmId;
    private String farmName;
    private String farmNameInKannada;
    private Long userMasterId;
    private String username;
}
