package com.sericulture.masterdata.model.api.divisionMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditDivisionMasterRequest extends RequestBody {

    @Schema(name = "divisionMasterId", example = "1")
    Long divisionMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Division name must contain only letters and numbers")
    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Division Name in kannada must contain only letters and numbers")
    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;
}
