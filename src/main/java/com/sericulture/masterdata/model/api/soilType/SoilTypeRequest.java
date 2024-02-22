package com.sericulture.masterdata.model.api.soilType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SoilTypeRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "SoilType must contain only letters and numbers")
    @Schema(name = "soilTypeName", example = "Red Soil", required = true)
    String soilTypeName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "SoilType in kannada must contain only letters and numbers")
    @Schema(name = "soilTypeNameInKannada",  example = "ಭಾಷೆ")
    String soilTypeNameInKannada;
}
