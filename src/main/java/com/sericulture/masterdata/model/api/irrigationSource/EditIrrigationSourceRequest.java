package com.sericulture.masterdata.model.api.irrigationSource;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditIrrigationSourceRequest extends RequestBody {
    @Schema(name = "irrigationSourceId", example = "1")
    Integer irrigationSourceId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Irrigation source must contain only letters and numbers")
    @Schema(name = "irrigationSourceName", example = "Rainfall", required = true)
    String irrigationSourceName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Irrigation source name in kannada must contain only letters and numbers")
    @Schema(name = "irrigationSourceNameInKannada",  example = "ಭಾಷೆ")
    String irrigationSourceNameInKannada;
}
