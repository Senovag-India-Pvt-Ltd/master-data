package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmTypeDTO {
    private Long farmTypeId;
    private Long farmId;
    private String name;
    private String nameInKannada;
    private String farmName;

}
