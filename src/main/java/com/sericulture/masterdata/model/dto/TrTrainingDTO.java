package com.sericulture.masterdata.model.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.format.DecimalStyle;

@Data
public class TrTrainingDTO {
    private Long trTraineeId;
    private String trTraineeName;
    private Long designationId;
    private Long trOfficeId;
    private Long gender;
    private String mobileNumber;
    private String place;
    private Long stateId;
    private Long districtId;
    private Long talukId;
    private Long hobliId;
    private Long villageId;
    private Long preTestScore;
    private Long postTestScore;
    private BigDecimal percentageImproved;
    private String name;
    private String trOfficeName;
    private String stateName;
    private String districtName;
    private String talukName;
    private String hobliName;
    private String villageName;

    public TrTrainingDTO(Long trTraineeId,
                         String trTraineeName,
                         Long designationId,
                         Long trOfficeId,
                         Long gender,
                         String mobileNumber,
                         String place,
                         Long stateId,
                         Long districtId,
                         Long talukId,
                         Long hobliId,
                         Long villageId,
                         Long preTestScore,
                         Long postTestScore,
                         BigDecimal percentageImproved,
                         String name,
                         String trOfficeName,
                         String stateName,
                         String districtName,
                         String talukName,
                         String hobliName,
                         String villageName){
        this.trTraineeId=trTraineeId;
        this.trTraineeName=trTraineeName;
        this.designationId=designationId;
        this.trOfficeId=trOfficeId;
        this.gender=gender;
        this.mobileNumber=mobileNumber;
        this.place=place;
        this.stateId=stateId;
        this.districtId=districtId;
        this.talukId=talukId;
        this.hobliId=hobliId;
        this.villageId=villageId;
        this.preTestScore=preTestScore;
        this.postTestScore=postTestScore;
        this.percentageImproved=percentageImproved;
        this.name=name;
        this.trOfficeName=trOfficeName;
        this.stateName=stateName;
        this.districtName=districtName;
        this.talukName=talukName;
        this.hobliName=hobliName;
        this.villageName=villageName;


    }

}
