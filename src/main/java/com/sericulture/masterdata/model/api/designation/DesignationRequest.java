package com.sericulture.masterdata.model.api.designation;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DesignationRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Designation must contain only letters and numbers")
    @Schema(name = "name", example = "Admin", required = true)
    String name;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Designation name in kannada must contain only letters and numbers")
    @Schema(name = "designationNameInKannada",  example = "ಭಾಷೆ")
    String designationNameInKannada;
}