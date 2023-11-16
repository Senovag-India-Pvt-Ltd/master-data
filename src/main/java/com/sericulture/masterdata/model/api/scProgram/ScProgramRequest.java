package com.sericulture.masterdata.model.api.scProgram;

import brave.Request;
import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScProgramRequest extends RequestBody {
    @Schema(name = "scProgramName", example = "scProgram 1", required = true)
    String scProgramName;
}
