package com.sericulture.masterdata.model.api.scProgram;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditScProgramRequest extends RequestBody {
    @Schema(name = "scProgramId", example = "1")
    Integer scProgramId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "scProgram must contain only letters and numbers")
    @Schema(name = "scProgramName", example = "scProgram 1 ", required = true)
    String scProgramName;
}
