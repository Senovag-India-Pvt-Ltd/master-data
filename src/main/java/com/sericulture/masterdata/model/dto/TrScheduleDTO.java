package com.sericulture.masterdata.model.dto;

import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr name must contain only letters and numbers")

    private String trName;
    @Pattern(regexp = "^[a-zA-Z0-9/_.\\s]*$", message = "Tr upload path must contain only letters and numbers")

    private String trUploadPath;

    private Date trStartDate;
    private Date trDateOfCompletion;
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must contain only letters and numbers")

    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr institution master name must contain only letters and numbers")

    private String trInstitutionMasterName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr program master name must contain only letters and numbers")

    private String trProgramMasterName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr course master name must contain only letters and numbers")

    private String trCourseMasterName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr group master name must contain only letters and numbers")

    private  String trGroupMasterName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr mode master name must contain only letters and numbers")

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
