package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScHeadAccountDTO {
    private Long scHeadAccountId;
    private String scHeadAccountName;
    private String scHeadAccountNameInKannada;
    private Long scSchemeDetailsId;
    private String dbtCode;
    private String schemeName;

}
