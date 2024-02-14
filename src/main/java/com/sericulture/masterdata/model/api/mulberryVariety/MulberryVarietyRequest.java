package com.sericulture.masterdata.model.api.mulberryVariety;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MulberryVarietyRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "MulberryVariety must contain only letters and numbers")
    @Schema(name = "mulberryVarietyName", example = "Mulberry variety 1", required = true)
    String mulberryVarietyName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "MulberryVariety in kannada must contain only letters and numbers")
    @Schema(name = "mulberryVarietyNameInKannada",  example = "ಭಾಷೆ")
    String mulberryVarietyNameInKannada;
}