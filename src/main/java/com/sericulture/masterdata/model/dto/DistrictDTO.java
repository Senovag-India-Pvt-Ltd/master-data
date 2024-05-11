package com.sericulture.masterdata.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DistrictDTO {
    private Long districtId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "District name must contain only letters and numbers")
    private String districtName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "District name in kannada must contain only letters and numbers")
    private String districtNameInKannada;
    private Long stateId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "lgDistrict must contain only letters and numbers")
    private String lgDistrict;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "State name must contain only letters and numbers")
    private String stateName;

    public DistrictDTO(){

    }

    public DistrictDTO(Long districtId, String districtName,String districtNameInKannada, Long stateId,String lgDistrict, String stateName) {
        this.districtId = districtId;
        this.districtName = districtName;
        this.districtNameInKannada = districtNameInKannada;
        this.stateId = stateId;
        this.lgDistrict = lgDistrict;
        this.stateName = stateName;
    }
}
