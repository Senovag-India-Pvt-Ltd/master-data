package com.sericulture.masterdata.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class MarketMasterDTO {

    private Long marketMasterId;
    private String marketMasterName;
    private String marketNameInKannada;
    private String marketMasterAddress;
    private BigDecimal boxWeight;
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
    private LocalTime auctionAcceptance1StartTime;
    private LocalTime auctionAcceptance2StartTime;
    private LocalTime auctionAcceptance3StartTime;
    private LocalTime auctionAcceptance1EndTime;
    private LocalTime auctionAcceptance2EndTime;
    private LocalTime auctionAcceptance3EndTime;
    private String serialNumberPrefix;
    private String clientId;
    private Long marketTypeMasterId;
    private BigDecimal marketLatitude;
    private BigDecimal marketLongitude;
    private BigDecimal radius;
    private String stateName;
    private String districtName;
    private String talukName;
    private String marketTypeMasterName;
    private Long reelerMinimumBalance;
    private String snorkelRequestPath;
    private String snorkelResponsePath;
    private String clientCode;
    private Long divisionMasterId;
    private String name;


    public MarketMasterDTO() {

    }
    public MarketMasterDTO
            (
        Long marketMasterId,
        String marketMasterName,
        String marketNameInKannada,
        String marketMasterAddress,
        BigDecimal boxWeight,
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
        LocalTime auctionAcceptance1StartTime,
        LocalTime auctionAcceptance2StartTime,
        LocalTime auctionAcceptance3StartTime,
        LocalTime auctionAcceptance1EndTime,
        LocalTime auctionAcceptance2EndTime,
        LocalTime auctionAcceptance3EndTime,
        String serialNumberPrefix,
        String clientId,
        Long marketTypeMasterId,
        BigDecimal marketLatitude,
        BigDecimal marketLongitude,
        BigDecimal radius,
        String snorkelRequestPath,
        String snorkelResponsePath,
        String clientCode,
        String stateName,
        String districtName,
        String talukName,
        String marketTypeMasterName,
        Long reelerMinimumBalance,
        Long divisionMasterId,
        String name
            ) {
        this.marketMasterId = marketMasterId;
        this.marketNameInKannada = marketNameInKannada;
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
        this.auctionAcceptance1StartTime = auctionAcceptance1StartTime;
        this.auctionAcceptance2StartTime = auctionAcceptance2StartTime;
        this.auctionAcceptance3StartTime = auctionAcceptance3StartTime;
        this.auctionAcceptance1EndTime = auctionAcceptance1EndTime;
        this.auctionAcceptance2EndTime = auctionAcceptance2EndTime;
        this.auctionAcceptance3EndTime = auctionAcceptance3EndTime;
        this.serialNumberPrefix = serialNumberPrefix;
        this.clientId = clientId;
        this.marketTypeMasterId = marketTypeMasterId;
        this.marketLatitude = marketLatitude;
        this.marketLongitude = marketLongitude;
        this.radius = radius;
        this.snorkelRequestPath = snorkelRequestPath;
        this.snorkelResponsePath = snorkelResponsePath;
        this.clientCode = clientCode;
        this.stateName = stateName;
        this.districtName = districtName;
        this.talukName = talukName;
        this.marketTypeMasterName = marketTypeMasterName;
        this.reelerMinimumBalance = reelerMinimumBalance;
        this.divisionMasterId = divisionMasterId;
        this.name = name;


    }
}
