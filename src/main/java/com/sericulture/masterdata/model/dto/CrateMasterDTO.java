package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class CrateMasterDTO {

    private Long crateMasterId;
    private Long raceMasterId;
    private Long marketId;
    private Long godownId;
    private Long approxWeightPerCrate;
    private String raceMasterName;
    private String marketMasterName;
    private String godownName;

    public CrateMasterDTO(){

    }
    public CrateMasterDTO(Long crateMasterId, Long raceMasterId, Long marketId, Long godownId, Long approxWeightPerCrate, String raceMasterName, String marketMasterName, String godownName) {
        this.crateMasterId = crateMasterId;
        this.raceMasterId = raceMasterId;
        this.marketId = marketId;
        this.godownId = godownId;
        this.approxWeightPerCrate = approxWeightPerCrate;
        this.raceMasterName = raceMasterName;
        this.marketMasterName = marketMasterName;
        this.godownName = godownName;
    }
}
