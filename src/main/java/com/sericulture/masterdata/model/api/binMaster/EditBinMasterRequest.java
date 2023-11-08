package com.sericulture.masterdata.model.api.binMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditBinMasterRequest extends RequestBody {
    @Schema(name="binMasterId", example = "1")
    Long binMasterId;

    @Schema(name = "binCounterMasterId", example = "1")
    Long binCounterMasterId;

    @Schema(name = "type", example = "1")
    Long type;

    @Schema(name = "binNumber", example = "12S",required=true)
    String binNumber;

    @Schema(name = "status", example = "1")
    Long status;
}
