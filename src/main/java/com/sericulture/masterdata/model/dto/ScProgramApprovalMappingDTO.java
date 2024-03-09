package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScProgramApprovalMappingDTO {
    private Long scProgramApprovalMappingId;
    private Long scProgramId;
    private Long scApprovalStageId;
    private Long designationId;
    private Long orders;
    private String scProgramName;
    private String stageName;
    private String name;


}
