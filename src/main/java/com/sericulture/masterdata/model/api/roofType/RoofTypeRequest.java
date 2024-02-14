package com.sericulture.masterdata.model.api.roofType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RoofTypeRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "RoofType must contain only letters and numbers")
    @Schema(name = "roofTypeName", example = "Hip Roof", required = true)
    String roofTypeName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "RoofType in kannada must contain only letters and numbers")
    @Schema(name = "roofTypeNameInKannada",  example = "ಭಾಷೆ")
    String roofTypeNameInKannada;
}
