package com.sericulture.masterdata.model.api.scProgram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScProgramResponse {

    @Schema(name = "scProgramId", example = "1")
    Integer scProgramId;

    @Schema(name = "scProgramName", example = "scProgram 1 ")
    String scProgramName;
}
