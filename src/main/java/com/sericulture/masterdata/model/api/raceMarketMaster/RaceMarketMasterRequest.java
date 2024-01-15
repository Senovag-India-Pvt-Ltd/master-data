package com.sericulture.masterdata.model.api.raceMarketMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RaceMarketMasterRequest extends RequestBody {

    @Schema(name = "marketMasterId", example = "1",required=true)
    Integer marketMasterId;

    @Schema(name = "raceMasterId", example = "1",required=true)
    Integer raceMasterId;

}
