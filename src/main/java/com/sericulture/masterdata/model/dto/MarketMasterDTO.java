package com.sericulture.masterdata.model.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class MarketMasterDTO {

    private Long marketMasterId;
    private String marketMasterName;
    private String marketMasterAddress;
    private Long boxWeight;
    private Long lotWeight;
    private Long stateId;
    private Long districtId;
    private Long talukId;
    private LocalTime issueBidSlipStartTime;
    private LocalTime issueBidSlipEndTime;
    private LocalTime auction1StartTime;
    private LocalTime auction2StartTime;
    private LocalTime auction3StartTime;
    private LocalTime auction1EndTime;
    private LocalTime auction2EndTime;
    private LocalTime auction3EndTime;
    private Long marketTypeMasterId;
    private String stateName;
    private String districtName;
    private String talukName;
    private String marketTypeMasterName;

    public MarketMasterDTO() {

    }
    public MarketMasterDTO
            (
        Long marketMasterId,
        String marketMasterName,
        String marketMasterAddress,
        Long boxWeight,
        Long lotWeight,
        Long stateId,
        Long districtId,
        Long talukId,
        LocalTime issueBidSlipStartTime,
        LocalTime issueBidSlipEndTime,
        LocalTime auction1StartTime,
        LocalTime auction2StartTime,
        LocalTime auction3StartTime,
        LocalTime auction1EndTime,
        LocalTime auction2EndTime,
        LocalTime auction3EndTime,
        Long marketTypeMasterId,
        String stateName,
        String districtName,
        String talukName,
        String marketTypeMasterName) {
        this.marketMasterId = marketMasterId;
        this.marketMasterName = marketMasterName;
        this.marketMasterAddress = marketMasterAddress;
        this.boxWeight = boxWeight;
        this.lotWeight = lotWeight;
        this.stateId = stateId;
        this.districtId = districtId;
        this.talukId = talukId;
        this.issueBidSlipStartTime = issueBidSlipStartTime;
        this.issueBidSlipEndTime = issueBidSlipEndTime;
        this.auction1StartTime = auction1StartTime;
        this.auction2StartTime = auction2StartTime;
        this.auction3StartTime = auction3StartTime;
        this.auction1EndTime = auction1EndTime;
        this.auction2EndTime = auction2EndTime;
        this.auction3EndTime = auction3EndTime;
        this.marketTypeMasterId = marketTypeMasterId;
        this.stateName = stateName;
        this.districtName = districtName;
        this.talukName = talukName;
        this.marketTypeMasterName = marketTypeMasterName;

    }
}
