package com.sericulture.masterdata.model.api.externalUnitType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditExternalUnitTypeRequest extends RequestBody {
    @Schema(name = "externalUnitTypeId", example = "1")
    Integer externalUnitTypeId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "External unit type name must contain only letters and numbers")
    @Schema(name = "externalUnitTypeName", example = "external unit type 1", required = true)
    String externalUnitTypeName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "External unit type name in kannada must contain only letters and numbers")
    @Schema(name = "externalUnitTypeNameInKannada",  example = "ಭಾಷೆ")
    String externalUnitTypeNameInKannada;
}
