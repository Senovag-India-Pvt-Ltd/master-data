package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class ScTscGeoMappingDTO {
    private Long scTscGeoMappingId;
    private Long districtId;
    private String districtName;
    private Long talukId;
    private String talukName;
    private Long hobliId;
    private String hobliName;
    private Long villageId;
    private String villageName;
    private Long tscMasterId;
    private String tscName;
    private Long officerUserId;
    private String officerName;

    public ScTscGeoMappingDTO() {
    }

    public ScTscGeoMappingDTO(Long scTscGeoMappingId, Long districtId, String districtName,
                               Long talukId, String talukName, Long hobliId, String hobliName,
                               Long villageId, String villageName, Long tscMasterId, String tscName,
                               Long officerUserId, String officerName) {
        this.scTscGeoMappingId = scTscGeoMappingId;
        this.districtId = districtId;
        this.districtName = districtName;
        this.talukId = talukId;
        this.talukName = talukName;
        this.hobliId = hobliId;
        this.hobliName = hobliName;
        this.villageId = villageId;
        this.villageName = villageName;
        this.tscMasterId = tscMasterId;
        this.tscName = tscName;
        this.officerUserId = officerUserId;
        this.officerName = officerName;
    }
}
