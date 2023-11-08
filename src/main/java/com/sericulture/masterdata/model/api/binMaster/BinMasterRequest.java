package com.sericulture.masterdata.model.api.binMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BinMasterRequest extends RequestBody {
    @Schema(name = "binCounterMasterId", example = "1", required = true)
    Long binCounterMasterId;

    @Schema(name = "type", example = "1", required = true)
    Long type;

    @Schema(name = "binNumber", example = "12S", required = true)
    String binNumber;

    @Schema(name = "status", example = "1", required = true)
    Long status;
}
