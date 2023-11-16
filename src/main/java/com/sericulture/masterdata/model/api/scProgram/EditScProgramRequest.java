package com.sericulture.masterdata.model.api.scProgram;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditScProgramRequest extends RequestBody {
    @Schema(name = "scProgramId", example = "1")
    Integer scProgramId;

    @Schema(name = "scProgramName", example = "scProgram 1 ", required = true)
    String scProgramName;
}
