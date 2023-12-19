package com.sericulture.masterdata.model.api.crateMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CrateMasterRequest extends RequestBody {

    @Schema(name = "raceMasterId", example = "1",required=true)
    Integer  raceMasterId;

    @Schema(name = "marketId", example = "1",required=true)
    Integer marketId;

    @Schema(name = "godownId", example = "1",required=true)
    Integer godownId;

    @Schema(name = "approxWeightPerCrate", example = "2",required=true)
    Integer approxWeightPerCrate;

}
