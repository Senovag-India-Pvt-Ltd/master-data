package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScComponentDTO {
    private Long scComponentId;
    private Long scSubSchemeDetailsId;
    private String scComponentName;
    private String subSchemeName;

}
