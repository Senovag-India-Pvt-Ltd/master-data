package com.sericulture.masterdata.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DistrictDTO {
    private Long districtId;
    private String districtName;
    private String districtNameInKannada;
    private Long stateId;
    private String stateName;

    public DistrictDTO(){

    }

    public DistrictDTO(Long districtId, String districtName,String districtNameInKannada, Long stateId, String stateName) {
        this.districtId = districtId;
        this.districtName = districtName;
        this.districtNameInKannada = districtNameInKannada;
        this.stateId = stateId;
        this.stateName = stateName;
    }
}
