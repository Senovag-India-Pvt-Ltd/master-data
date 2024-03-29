package com.sericulture.masterdata.model.api.grainageMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditGrainageMasterRequest extends RequestBody {
    @Schema(name = "grainageMasterId", example = "1")
    Long grainageMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "grainage name must contain only letters and numbers")
    @Schema(name = "grainageMasterName", example = "Karnataka", required = true)
    String grainageMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "grainage in kannada must contain only letters and numbers")
    @Schema(name = "grainageMasterNameInKannada", example = "ಭಾಷೆ", required = true)
    String grainageMasterNameInKannada;

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;
}
