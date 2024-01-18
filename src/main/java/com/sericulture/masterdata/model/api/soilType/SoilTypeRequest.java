package com.sericulture.masterdata.model.api.soilType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SoilTypeRequest extends RequestBody {
    @Schema(name = "soilTypeName", example = "Red Soil", required = true)
    String soilTypeName;

    @Schema(name = "soilTypeNameInKannada",  example = "ಭಾಷೆ")
    String soilTypeNameInKannada;
}
