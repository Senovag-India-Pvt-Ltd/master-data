package com.sericulture.masterdata.model.api.binCounterMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BinCounterMasterRequest extends RequestBody {

    @Schema(name = "binCounterMasterId", example = "1", required=true)
    Integer binCounterMasterId;

    @Schema(name = "marketId", example = "1", required=true)
    Long marketId;

    @Schema(name = "godownId", example = "1", required=true)
    Long godownId;

    @Schema(name = "smallBinStart", example = "2", required=true)
    Long smallBinStart;

    @Schema(name = "smallBinEnd", example = "3", required=true)
    Long smallBinEnd;

    @Schema(name = "bigBinStart", example = "10", required=true)
    Long bigBinStart;

    @Schema(name = "bigBinEnd", example = "5", required=true)
    Long bigBinEnd;
}
