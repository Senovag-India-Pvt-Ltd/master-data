package com.sericulture.masterdata.model.api.binCounterMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditBinCounterMasterRequest extends RequestBody {
    @Schema(name = "binCounterMasterId", example = "1")
    Integer binCounterMasterId;

    @Schema(name = "marketId", example = "1")
    Long marketId;

    @Schema(name = "godownId", example = "1")
    Long godownId;

    @Schema(name = "smallBinStart", example = "2")
    Long smallBinStart;

    @Schema(name = "smallBinEnd", example = "3")
    Long smallBinEnd;

    @Schema(name = "bigBinStart", example = "10")
    Long bigBinStart;

    @Schema(name = "bigBinEnd", example = "5")
    Long bigBinEnd;

}
