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
    @Schema(name="binMasterId", example = "1")
    int binMasterId;

    @Schema(name = "binCounterMasterId", example = "1")
    int binCounterMasterId;

    @Schema(name = "type", example = "1")
    int type;

    @Schema(name = "binNumber", example = "12S")
    String binNumber;

    @Schema(name = "status", example = "1")
    int status;
}
