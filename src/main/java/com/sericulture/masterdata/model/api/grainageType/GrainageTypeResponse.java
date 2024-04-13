package com.sericulture.masterdata.model.api.grainageType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrainageTypeResponse {
    @Schema(name = "grainageTypeId", example = "1")
    Long grainageTypeId;

    @Schema(name = "grainageMasterId", example = "1")
    Long grainageMasterId;

    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;

    @Schema(name = "grainageMasterName", example = "Karnataka", required = true)
    String grainageMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
