package com.sericulture.masterdata.model.api.marketMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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

    @Schema(name = "marketMasterAddress", example = "Udupi")
    String marketMasterAddress;

    @Schema(name = "boxWeight", example = "10")
    Integer boxWeight;

    @Schema(name = "lotWeight", example = "5")
    Long lotWeight;

    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Integer districtId;

    @Schema(name = "talukId", example = "1")
    Integer talukId;

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


    @Schema(name = "marketTypeMasterId", example = "1")
    Integer marketTypeMasterId;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
