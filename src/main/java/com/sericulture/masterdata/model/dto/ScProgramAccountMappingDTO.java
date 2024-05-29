package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScProgramAccountMappingDTO {
    private Long scProgramAccountMappingId;
    private Long scProgramId;
    private Long scHeadAccountId;
    private Long scCategoryId;
    private String scProgramName;
    private String scHeadAccountName;
    private String categoryName;
}
