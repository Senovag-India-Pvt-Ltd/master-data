package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class UserPreferenceDTO {

    private Long userPreferenceId;
    private Long userMasterId;
    private Long godownId;
    private String username;
    private String godownName;

    public UserPreferenceDTO(){

    }

    public UserPreferenceDTO(Long userPreferenceId,
                             Long userMasterId,
                             Long godownId,
                             String username,
                             String godownName)
    {
        this.userPreferenceId = userPreferenceId;
        this.userMasterId = userMasterId;
        this.godownId = godownId;
        this.username = username;
        this.godownName = godownName;
    }
}
