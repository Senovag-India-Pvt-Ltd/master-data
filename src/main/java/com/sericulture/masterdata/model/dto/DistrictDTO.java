package com.sericulture.masterdata.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DistrictDTO {
    private Long districtId;
    private String districtName;
    private Long stateId;

    private String stateName;

    public DistrictDTO(){

    }

    public DistrictDTO(Long districtId, String districtName, Long stateId, String stateName) {
        this.districtId = districtId;
        this.districtName = districtName;
        this.stateId = stateId;
        this.stateName = stateName;
    }
}
