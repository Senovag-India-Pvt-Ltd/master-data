package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScSubSchemeMappingDTO {
    private Long scSubSchemeMappingId;
    private Long scSchemeDetailsId;
    private Long scSubSchemeDetailsId;
    private String schemeName;
    private String subSchemeName;
}
