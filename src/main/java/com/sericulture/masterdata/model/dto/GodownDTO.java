package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class GodownDTO {

    private Long godownId;
    private String godownName;
    private Long marketMasterId;
    private String marketMasterName;

    public GodownDTO(){

    }

    public GodownDTO(Long godownId, String godownName, Long marketMasterId, String marketMasterName) {
        this.godownId = godownId;
        this.godownName = godownName;
        this.marketMasterId = marketMasterId;
        this.marketMasterName = marketMasterName;
    }
}
