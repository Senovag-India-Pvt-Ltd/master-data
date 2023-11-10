package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class HobliDTO {
    private Long stateId;
    private String stateName;
    private Long districtId;
    private String districtName;
    private Long talukId;
    private String talukName;
    private Long hobliId;
    private String hobliName;


    public HobliDTO(){

    }
    public HobliDTO(Long hobliId, String hobliName,Long stateId,Long districtId,Long talukId,String stateName,String districtName,String talukName) {
        this.hobliId = hobliId;
        this.hobliName = hobliName;
        this.stateId = stateId;
        this.stateName = stateName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.talukId = talukId;
        this.talukName = talukName;
    }
}
