package com.sericulture.masterdata.model.api.district;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DistrictRequest extends RequestBody {
    @Schema(name = "stateId", example = "1", required = true)
    Long stateId;

    @Schema(name = "districtName", example = "Shimoga", required = true)
    String districtName;

    @Schema(name = "districtNameInKannada",  example = "ಭಾಷೆ")
    String districtNameInKannada;
}