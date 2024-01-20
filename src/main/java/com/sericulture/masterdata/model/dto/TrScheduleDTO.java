package com.sericulture.masterdata.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TrScheduleDTO {
    private Long trScheduleId;
    private Long userMasterId;
    private Long trStakeholderType;
    private Long trInstitutionMasterId;
    private Long trGroupMasterId;
    private Long trProgramMasterId;
    private Long trCourseMasterId;
    private Long trModeMasterId;
    private Long trDuration;
    private Long trPeriod;
    private Long trNoOfParticipant;
    private String trName;
    private String trUploadPath;
    private Date trStartDate;
    private Date trDateOfCompletion;
    private String username;
    private String trInstitutionMasterName;
    private String trProgramMasterName;
    private String trCourseMasterName;
    private  String trGroupMasterName;
    private String trModeMasterName;



    public TrScheduleDTO(Long trScheduleId,
                         Long trInstitutionMasterId,
                         Long userMasterId,
                         Long trStakeholderType,
                         Long trGroupMasterId,
                         Long trProgramMasterId,
                         Long trCourseMasterId,
                         Long trModeMasterId,
                         Long trDuration,
                         Long trPeriod,
                         Long trNoOfParticipant,
                         String trName,
                         String trUploadPath,
                         Date trStartDate,
                         Date trDateOfCompletion,
                         String username,
                         String trInstitutionMasterName,
                         String trGroupMasterName,
                         String trProgramMasterName,
                         String trCourseMasterName,
                         String trModeMasterName){
        this.trScheduleId= trScheduleId;
        this.userMasterId=userMasterId;
        this.trStakeholderType=trStakeholderType;
        this.trInstitutionMasterId=trInstitutionMasterId;
        this.trGroupMasterId=trGroupMasterId;
        this.trProgramMasterId=trProgramMasterId;
        this.trCourseMasterId=trCourseMasterId;
        this.trModeMasterId=trModeMasterId;
        this.trDuration=trDuration;
        this.trPeriod=trPeriod;
        this.trNoOfParticipant=trNoOfParticipant;
        this.trName=trName;
        this.trUploadPath=trUploadPath;
        this.trStartDate=trStartDate;
        this.trDateOfCompletion=trDateOfCompletion;
        this.username=username;
        this.trInstitutionMasterName=trInstitutionMasterName;
        this.trGroupMasterName=trGroupMasterName;
        this.trProgramMasterName=trProgramMasterName;
        this.trCourseMasterName=trCourseMasterName;
        this.trModeMasterName =trModeMasterName;
    }
}
