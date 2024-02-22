package com.sericulture.masterdata.model.api.plantationType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditPlantationTypeRequest extends RequestBody {
    @Schema(name = "plantationTypeId", example = "1")
    Integer plantationTypeId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "PlantationType must contain only letters and numbers")
    @Schema(name = "plantationTypeName", example = "Mulberry Silk", required = true)
    String plantationTypeName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "PlantationType in kannada must contain only letters and numbers")
    @Schema(name = "plantationTypeNameInKannada",  example = "ಭಾಷೆ")
    String plantationTypeNameInKannada;
}
