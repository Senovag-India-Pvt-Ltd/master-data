package com.sericulture.masterdata.model.api.marketMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MarketMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Market must contain only letters and numbers")
    @Schema(name = "marketMasterName", example = "Kaveri", required = true)
    String marketMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Market name in kannada must contain only letters and numbers")
    @Schema(name = "marketNameInKannada",  example = "ಭಾಷೆ")
    String marketNameInKannada;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Market Address must contain only letters and numbers")
    @Schema(name = "marketMasterAddress", example = "Udupi", required = true)
    String marketMasterAddress;

    @Schema(name = "boxWeight", example = "10", required = true)
    BigDecimal boxWeight;

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

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Serial Number must contain only letters and numbers")
    @Schema(name = "serialNumberPrefix", example = "KLR")
    String serialNumberPrefix;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Client Id must contain only letters and numbers")
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

    @Schema(name = "reelerMinimumBalance", example = "1")
    Long reelerMinimumBalance;

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
}
