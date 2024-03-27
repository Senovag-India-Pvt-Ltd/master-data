package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScHeadAccountCategoryDTO {
    private Long scHeadAccountCategoryId;
    private Long scHeadAccountId;
    private Long scCategoryId;
    private String scHeadAccountName;
    private String categoryName;
}
