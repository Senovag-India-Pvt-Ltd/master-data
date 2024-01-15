package com.sericulture.masterdata.model.api.raceMarketMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceMarketMasterResponse {
    @Schema(name = "raceMarketMasterId", example = "1")
    Integer raceMarketMasterId;

    @Schema(name = "marketMasterId", example = "1")
    Integer marketMasterId;

    @Schema(name = "raceMasterId", example = "1")
    Integer raceMasterId;

    @Schema(name="marketMasterName", example = "Udupi")
    String marketMasterName;

    @Schema(name="raceMasterName", example = "kundapurr")
    String raceMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
