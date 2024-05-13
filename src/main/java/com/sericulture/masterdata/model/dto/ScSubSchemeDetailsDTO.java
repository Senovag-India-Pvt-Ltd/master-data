package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScSubSchemeDetailsDTO {
    private Long scSubSchemeDetailsId;
    private Long scSchemeDetailsId;
    private String subSchemeName;
    private String subSchemeNameInKannada;
    private Long subSchemeType;
    private Date subSchemeStartDate;
    private Date subSchemeEndDate;
    private Boolean withLand;
    private Long beneficiaryType;
    private String dbtCode;
    private String schemeName;



}
