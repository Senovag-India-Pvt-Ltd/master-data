package com.sericulture.masterdata.model.api.irrigationType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class IrrigationTypeRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Irrigation type must contain only letters and numbers")
    @Schema(name = "irrigationTypeName", example = "Flood", required = true)
    String irrigationTypeName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Irrigation type name in kannada must contain only letters and numbers")
    @Schema(name = "irrigationTypeNameInKannada",  example = "ಭಾಷೆ")
    String irrigationTypeNameInKannada;
}
