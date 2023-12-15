package com.sericulture.masterdata.model.api.marketMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MarketMasterRequest extends RequestBody {

    @Schema(name = "marketMasterName", example = "Kaveri", required = true)
    String marketMasterName;

    @Schema(name = "marketMasterAddress", example = "Udupi", required = true)
    String marketMasterAddress;

    @Schema(name = "boxWeight", example = "10", required = true)
    Integer boxWeight;

    @Schema(name = "lotWeight", example = "5", required = true)
    Integer lotWeight;

    @Schema(name = "stateId", example = "1", required = true)
    Long stateId;

    @Schema(name = "districtId", example = "1", required = true)
    Long districtId;

    @Schema(name = "talukId", example = "1", required = true)
    Long talukId;


    @Schema(name = "marketTypeMasterId", example = "1")
    Long marketTypeMasterId;
}
