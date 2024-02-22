package com.sericulture.masterdata.model.api.deputedInstituteMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditDeputedInstituteMasterRequest extends RequestBody {

    @Schema(name = "deputedInstituteId", example = "1")
    Integer deputedInstituteId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Deputed institute name must contain only letters and numbers")
    @Schema(name = "deputedInstituteName", example = "external unit type 1", required = true)
    String deputedInstituteName;
}
