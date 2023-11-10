package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class HobliDTO {
    private Long hobliId;
    private String hobliName;
    private Long stateId;
    private Long districtId;
    private Long talukId;
    private String stateName;
    private String districtName;
    private String talukName;

    public HobliDTO(){

    }

    public HobliDTO(Long hobliId, String hobliName, Long stateId, Long districtId, Long talukId, String stateName, String districtName, String talukName) {
        this.hobliId = hobliId;
        this.hobliName = hobliName;
        this.stateId = stateId;
        this.districtId = districtId;
        this.talukId = talukId;
        this.stateName = stateName;
        this.districtName = districtName;
        this.talukName = talukName;
    }
}
