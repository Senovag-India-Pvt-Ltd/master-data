package com.sericulture.masterdata.model.api.plantationType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditPlantationTypeRequest extends RequestBody {
    @Schema(name = "plantationTypeId", example = "1")
    Integer plantationTypeId;

    @Schema(name = "plantationTypeName", example = "Mulberry Silk", required = true)
    String plantationTypeName;

    @Schema(name = "plantationTypeNameInKannada",  example = "ಭಾಷೆ")
    String plantationTypeNameInKannada;
}
