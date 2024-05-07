package com.sericulture.masterdata.model.api.grainageMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrainageMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Grainage name must contain only letters and numbers")
    @Schema(name = "grainageMasterName", example = "Karnataka", required = true)
    String grainageMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Grainage in kannada must contain only letters and numbers")
    @Schema(name = "grainageMasterNameInKannada", example = "ಭಾಷೆ", required = true)
    String grainageMasterNameInKannada;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "grainage type must contain only letters and numbers")
    @Schema(name = "grainageType", example = "Karnataka", required = true)
    String grainageType;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "grainage name representation must contain only letters and numbers")
    @Schema(name = "grainageNameRepresentation", example = "Karnataka", required = true)
    String grainageNameRepresentation;


    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;
}
