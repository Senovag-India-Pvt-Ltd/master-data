package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HdQuestionMasterDTO {
    private Long hdQuestionId;
    private String hdQuestionName;
    private String hdQuestionAnswerName;
    private String hdFaqUploadPath;
}
