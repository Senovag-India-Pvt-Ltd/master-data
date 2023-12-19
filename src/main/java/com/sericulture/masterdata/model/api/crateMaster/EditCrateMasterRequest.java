package com.sericulture.masterdata.model.api.crateMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditCrateMasterRequest extends RequestBody {

    @Schema(name = "crateMasterId", example = "1")
    Long crateMasterId;

    @Schema(name = "raceMasterId", example = "1")
    Long raceMasterId;

    @Schema(name = "marketId", example = "1")
    Long marketId;

    @Schema(name = "godownId", example = "1")
    Long godownId;

    @Schema(name = "approxWeightPerCrate", example = "2")
    Long approxWeightPerCrate;


}
