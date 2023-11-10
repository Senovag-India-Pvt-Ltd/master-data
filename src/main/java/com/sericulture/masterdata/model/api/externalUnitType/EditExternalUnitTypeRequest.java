package com.sericulture.masterdata.model.api.externalUnitType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditExternalUnitTypeRequest extends RequestBody {
    @Schema(name = "externalUnitTypeId", example = "1")
    Integer externalUnitTypeId;

    @Schema(name = "externalUnitTypeName", example = "external unit type 1", required = true)
    String externalUnitTypeName;
}
