package com.sericulture.masterdata.model.api.binCounterMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinCounterMasterResponse {
    @Schema(name = "binCounterMasterId", example = "1")
    long binCounterMasterId;

    @Schema(name = "marketId", example = "1")
    int marketId;

    @Schema(name = "godownId", example = "1")
    int godownId;

    @Schema(name = "smallBinStart", example = "2")
    int smallBinStart;

    @Schema(name = "smallBinEnd", example = "3")
    int smallBinEnd;

    @Schema(name = "bigBinStart", example = "10")
    int bigBinStart;

    @Schema(name = "bigBinEnd", example = "5")
    int bigBinEnd;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
