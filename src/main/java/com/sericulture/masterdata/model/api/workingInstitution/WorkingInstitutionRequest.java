package com.sericulture.masterdata.model.api.workingInstitution;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkingInstitutionRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Working institution name must contain only letters and numbers")
    @Schema(name = "workingInstitutionName", example = "Karnataka", required = true)
    String workingInstitutionName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Working institution name in kannada must contain only letters and numbers")
    @Schema(name = "workingInstitutionNameInKannada",  example = "ಭಾಷೆ")
    String workingInstitutionNameInKannada;
}
