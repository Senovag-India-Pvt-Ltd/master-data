package com.sericulture.masterdata.model.api.silkwormvariety;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SilkWormVarietyRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "SilkWormVariety must contain only letters and numbers")
    @Schema(name = "silkWormVarietyName", example = "Bombyx Mori", required = true)
    String silkWormVarietyName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "SilkWormVariety in kannada must contain only letters and numbers")
    @Schema(name = "silkWormVarietyNameInKannada",  example = "ಭಾಷೆ")
    String silkWormVarietyNameInKannada;
}