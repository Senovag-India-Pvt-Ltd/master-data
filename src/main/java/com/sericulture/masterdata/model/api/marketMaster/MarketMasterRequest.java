package com.sericulture.masterdata.model.api.marketMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MarketMasterRequest extends RequestBody {

    @Schema(name = "marketMasterName", example = "Kaveri", required = true)
    String marketMasterName;

    @Schema(name = "marketMasterAddress", example = "Udupi", required = true)
    String marketMasterAddress;

    @Schema(name = "boxWeight", example = "10", required = true)
    Integer boxWeight;

    @Schema(name = "lotWeight", example = "5", required = true)
    Integer lotWeight;

    @Schema(name = "stateId", example = "1", required = true)
    Long stateId;

    @Schema(name = "districtId", example = "1", required = true)
    Long districtId;

    @Schema(name = "talukId", example = "1", required = true)
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


    @Schema(name = "marketTypeMasterId", example = "1")
    Long marketTypeMasterId;
}
