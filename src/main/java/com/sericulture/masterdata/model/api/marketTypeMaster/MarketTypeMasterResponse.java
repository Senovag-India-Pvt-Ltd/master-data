package com.sericulture.masterdata.model.api.marketTypeMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketTypeMasterResponse {

    @Schema(name = "marketTypeMasterId", example = "1")
    Integer marketTypeMasterId;

    @Schema(name = "marketTypeMasterName", example = "Commercial Market")
    String marketTypeMasterName;

    @Schema(name = "reelerFee", example = "2.4")
    BigDecimal reelerFee;

    @Schema(name = "farmerFee", example = "2.3")
    BigDecimal farmerFee;

    @Schema(name = "traderFee", example = "1.2")
    BigDecimal traderFee;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
