package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class HobliDTO {
    private Long hobliId;
    private String hobliName;
    private String hobliNameInKannada;
    private Long stateId;
    private Long districtId;
    private Long talukId;
    private String hobliCode;
    private String stateName;
    private String districtName;
    private String talukName;

    public HobliDTO(){

    }

    public HobliDTO(Long hobliId, String hobliName, String hobliNameInKannada,Long stateId, Long districtId, Long talukId, String hobliCode,String stateName, String districtName, String talukName) {
        this.hobliId = hobliId;
        this.hobliName = hobliName;
        this.hobliNameInKannada = hobliNameInKannada;
        this.stateId = stateId;
        this.districtId = districtId;
        this.talukId = talukId;
        this.hobliCode = hobliCode;
        this.stateName = stateName;
        this.districtName = districtName;
        this.talukName = talukName;
    }
}
