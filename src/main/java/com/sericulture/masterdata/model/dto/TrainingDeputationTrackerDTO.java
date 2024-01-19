package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDeputationTrackerDTO {
    private Long trainingDeputationId;
    private String officialName;
    private Long designationId;
    private String officialAddress;
    private String mobileNumber;
    private Long deputedInstituteId;
    private Date deputedFromDate;
    private Date deputedToDate;
    private Long trProgramMasterId;
    private Long trCourseMasterId;
    private int deputedAttended;
    private String deputedRemarks;
    private String name;
    private String deputedInstituteName;
    private String trProgramMasterName;
    private String trCourseMasterName;
}
