package com.sericulture.masterdata.model.api.generationNumberMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerationNumberMasterResponse {
    @Schema(name="generationNumberId", example = "1")
    Long generationNumberId;

    @Schema(name = "generationNumber", example = "Karnataka")
    String generationNumber;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}