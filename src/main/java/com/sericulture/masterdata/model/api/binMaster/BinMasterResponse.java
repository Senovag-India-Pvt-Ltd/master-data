package com.sericulture.masterdata.model.api.binMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinMasterResponse {

    @Schema(name = "binMasterId", example = "1")
    int binMasterId;

    @Schema(name = "binCounterMasterId", example = "1")
    int binCounterMasterId;

    @Schema(name = "godownId", example = "1")
    int godownId;

    @Schema(name = "marketId", example = "1")
    int marketId;

    @Schema(name = "binNumber", example = "12S")
    int binNumber;

    @Schema(name = "type", example = "Small")
    String type;

    @Schema(name = "status", example = "Big")
    String status;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
