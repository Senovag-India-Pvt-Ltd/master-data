package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class RaceMarketMasterDTO {
    private Long raceMarketMasterId;
    private Long marketMasterId;
    private Long raceMasterId;
    private String marketMasterName;
    private String raceMasterName;

    public RaceMarketMasterDTO(){

    }
    public RaceMarketMasterDTO(Long raceMarketMasterId,
                               Long marketMasterId,
                               Long raceMasterId,
                               String marketMasterName,
                               String raceMasterName) {
        this.raceMarketMasterId = raceMarketMasterId;
        this.marketMasterId = marketMasterId;
        this.raceMasterId = raceMasterId;
        this.marketMasterName = marketMasterName;
        this.raceMasterName = raceMasterName;

    }
}
