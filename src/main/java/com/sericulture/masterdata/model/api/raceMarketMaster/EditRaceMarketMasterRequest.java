package com.sericulture.masterdata.model.api.raceMarketMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditRaceMarketMasterRequest extends RequestBody {
    @Schema(name = "raceMarketMasterId", example = "1")
    Long raceMarketMasterId;

    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;

    @Schema(name = "raceMasterId", example = "1")
    Long raceMasterId;


}
