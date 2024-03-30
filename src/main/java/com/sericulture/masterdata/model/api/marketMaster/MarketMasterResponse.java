package com.sericulture.masterdata.model.api.marketMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketMasterResponse {
    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;

    @Schema(name = "marketMasterName", example = "Kaveri")
    String marketMasterName;

    @Schema(name = "marketNameInKannada",  example = "ಭಾಷೆ")
    String marketNameInKannada;

    @Schema(name = "marketMasterAddress", example = "Udupi")
    String marketMasterAddress;

    @Schema(name = "boxWeight", example = "10")
    BigDecimal boxWeight;

    @Schema(name = "lotWeight", example = "5")
    Long lotWeight;

    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Long districtId;

    @Schema(name = "talukId", example = "1")
    Long talukId;

    @Schema(name = "issueBidSlipStartTime", example = "1")
    LocalTime issueBidSlipStartTime;

    @Schema(name = "issueBidSlipEndTime", example = "1")
    LocalTime issueBidSlipEndTime;

    @Schema(name = "auction1StartTime", example = "1")
    LocalTime auction1StartTime;

    @Schema(name = "auction2StartTime", example = "1")
    LocalTime auction2StartTime;

    @Schema(name = "auction3StartTime", example = "1")
    LocalTime auction3StartTime;

    @Schema(name = "auction1EndTime", example = "1")
    LocalTime auction1EndTime;

    @Schema(name = "auction2EndTime", example = "1")
    LocalTime auction2EndTime;

    @Schema(name = "auction3EndTime", example = "1")
    LocalTime auction3EndTime;

    @Schema(name = "auctionAcceptance1StartTime", example = "00:00:00")
    LocalTime auctionAcceptance1StartTime;

    @Schema(name = "auctionAcceptance2StartTime", example = "00:00:00")
    LocalTime auctionAcceptance2StartTime;

    @Schema(name = "auctionAcceptance3StartTime", example = "00:00:00")
    LocalTime auctionAcceptance3StartTime;

    @Schema(name = "auctionAcceptance1EndTime", example = "00:00:00")
    LocalTime auctionAcceptance1EndTime;

    @Schema(name = "auctionAcceptance2EndTime", example = "00:00:00")
    LocalTime auctionAcceptance2EndTime;

    @Schema(name = "auctionAcceptance3EndTime", example = "00:00:00")
    LocalTime auctionAcceptance3EndTime;

    @Schema(name = "serialNumberPrefix", example = "KLR")
    String serialNumberPrefix;

    @Schema(name = "clientId", example = "KLR123")
    String clientId;

    @Schema(name = "marketTypeMasterId", example = "1")
    Long marketTypeMasterId;

    @Schema(name = "marketLatitude", example = "1.6")
    BigDecimal marketLatitude;

    @Schema(name = "marketLongitude", example = "1.7")
    BigDecimal marketLongitude;

    @Schema(name = "radius", example = "1.8")
    BigDecimal radius;

    @Schema(name = "stateName", example = "1")
    String stateName;

    @Schema(name = "districtName", example = "1")
    String districtName;

    @Schema(name = "marketTypeMasterId", example = "1")
    String talukName;

    @Schema(name = "marketTypeMasterName", example = "1")
    String marketTypeMasterName;

    @Schema(name = "reelerMinimumBalance", example = "1")
    Long reelerMinimumBalance;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

    @Schema(name = "snorkelRequestPath",example = "/path")
    String snorkelRequestPath;

    @Schema(name = "snorkelResponsePath",example = "/path")
    String snorkelResponsePath;

    @Schema(name = "clientCode",example = "C001")
    String clientCode;

    @Schema(name = "weighmentTripletGeneration", example = "true")
    Boolean weighmentTripletGeneration;

    @Schema(name = "bidAmountFlag", example = "true")
    Boolean bidAmountFlag;

    @Schema(name = "divisionMasterId", example = "1")
    Long divisionMasterId;

    @Schema(name = "name", example = "Karnataka")
    String name;
}
