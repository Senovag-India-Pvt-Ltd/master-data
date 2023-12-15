package com.sericulture.masterdata.model.api.marketMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditMarketMasterRequest extends RequestBody {
    @Schema(name = "marketMasterId", example = "1")
    Integer marketMasterId;

    @Schema(name = "marketMasterName", example = "Kaveri", required=true)
    String marketMasterName;

    @Schema(name = "marketMasterAddress", example = "Udupi", required=true)
    String marketMasterAddress;

    @Schema(name = "boxWeight", example = "10")
    Integer boxWeight;

    @Schema(name = "lotWeight", example = "5")
    Integer lotWeight;

    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Integer districtId;

    @Schema(name = "talukId", example = "1")
    Integer talukId;

    @Schema(name = "marketTypeMasterId", example = "1")
    Integer marketTypeMasterId;

}
