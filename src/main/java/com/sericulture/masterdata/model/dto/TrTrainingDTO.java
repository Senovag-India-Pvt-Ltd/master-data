package com.sericulture.masterdata.model.dto;


import lombok.Data;

import java.util.Date;

@Data
public class TrTrainingDTO {
    private Long trTrainingId;
    private Long trStakeholderType;
    private Long trInstitutionMasterId;
    private Long trGroupMasterId;
    private Long trProgramMasterId;
    private Long trCourseMasterId;
    private Date trTrainingDate;
    private Long trDuration;
    private Long trPeriod;
    private Long userMasterId;
    private String trInstitutionMasterName;
    private  String trGroupMasterName;
    private String trProgramMasterName;
    private String trCourseMasterName;
    private String username;

    public TrTrainingDTO(Long trTrainingId,
                         Long trStakeholderType,
                         Long trInstitutionMasterId,
                         Long trGroupMasterId,
                         Long trProgramMasterId,
                         Long trCourseMasterId,
                         Date trTrainingDate,
                         Long trDuration,
                         Long trPeriod,
                         Long userMasterId,
                         String trInstitutionMasterName,
                         String trGroupMasterName,
                         String trProgramMasterName,
                         String trCourseMasterName,
                         String username){
        this.trTrainingId= trTrainingId;
        this.trStakeholderType=trStakeholderType;
        this.trInstitutionMasterId=trInstitutionMasterId;
        this.trGroupMasterId=trGroupMasterId;
        this.trProgramMasterId=trProgramMasterId;
        this.trCourseMasterId=trCourseMasterId;
        this.trTrainingDate=trTrainingDate;
        this.trDuration=trDuration;
        this.trPeriod=trPeriod;
        this.userMasterId=userMasterId;
        this.trInstitutionMasterName=trInstitutionMasterName;
        this.trGroupMasterName=trGroupMasterName;
        this.trProgramMasterName=trProgramMasterName;
        this.trCourseMasterName=trCourseMasterName;
        this.username =username;

    }


}
