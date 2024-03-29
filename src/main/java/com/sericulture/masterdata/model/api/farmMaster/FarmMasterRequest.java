package com.sericulture.masterdata.model.api.farmMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class FarmMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Farm name must contain only letters and numbers")
    @Schema(name = "farmName", example = "Karnataka", required = true)
    String farmName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Farm in kannada must contain only letters and numbers")
    @Schema(name = "farmNameInKannada", example = "ಭಾಷೆ", required = true)
    String farmNameInKannada;

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;
}
