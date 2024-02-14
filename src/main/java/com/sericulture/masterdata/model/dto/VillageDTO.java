package com.sericulture.masterdata.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VillageDTO {
    private Long villageId;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Village must contain only letters and numbers")

    private String villageName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Village name in kannada must contain only letters and numbers")

    private String villageNameInKannada;
    private Long stateId;
    private Long districtId;
    private Long talukId;
    private Long hobliId;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "State name must contain only letters and numbers")

    private String stateName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "District name must contain only letters and numbers")

    private String districtName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Taluk name must contain only letters and numbers")

    private String talukName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Hobli name must contain only letters and numbers")

    private String hobliName;

    public VillageDTO(){

    }

    public VillageDTO(Long villageId,String villageName,String villageNameInKannada, Long stateId, Long districtId, Long talukId,Long hobliId, String stateName, String districtName, String talukName, String hobliName) {
        this.villageId = villageId;
        this.villageName = villageName;
        this.villageNameInKannada = villageNameInKannada;
        this.stateId = stateId;
        this.districtId = districtId;
        this.talukId = talukId;
        this.hobliId = hobliId;
        this.stateName = stateName;
        this.districtName = districtName;
        this.talukName = talukName;
        this.hobliName = hobliName;
    }
}
