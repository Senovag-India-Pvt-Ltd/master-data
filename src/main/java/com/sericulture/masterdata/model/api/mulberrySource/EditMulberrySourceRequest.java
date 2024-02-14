package com.sericulture.masterdata.model.api.mulberrySource;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditMulberrySourceRequest extends RequestBody {

    @Schema(name = "mulberrySourceId", example = "1")
    Integer mulberrySourceId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "MulberrySource must contain only letters and numbers")
    @Schema(name = "mulberrySourceName", example = "Mulberry source 1", required = true)
    String mulberrySourceName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "MulberrySource in kannada must contain only letters and numbers")
    @Schema(name = "mulberrySourceNameInKannada",  example = "ಭಾಷೆ")
    String mulberrySourceNameInKannada;
}