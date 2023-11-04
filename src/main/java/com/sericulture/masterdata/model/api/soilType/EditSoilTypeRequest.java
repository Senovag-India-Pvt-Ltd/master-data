package com.sericulture.masterdata.model.api.soilType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditSoilTypeRequest extends RequestBody {
    @Schema(name = "soilTypeId", example = "1")
    Integer soilTypeId;

    @Schema(name = "soilTypeName", example = "Red Soil", required = true)
    String soilTypeName;

}
