package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceMasterDTO {
    private Long raceMasterId;
    private String raceMasterName;
    private Long marketMasterId;
    private String marketMasterName;
}
