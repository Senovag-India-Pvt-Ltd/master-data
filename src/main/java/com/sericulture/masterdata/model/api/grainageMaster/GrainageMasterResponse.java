package com.sericulture.masterdata.model.api.grainageMaster;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrainageMasterResponse {

    @Schema(name = "grainageMasterId", example = "1")
    Integer grainageMasterId;

    @Schema(name = "grainageMasterName", example = "Karnataka", required = true)
    String grainageMasterName;

    @Schema(name = "grainageMasterNameInKannada", example = "ಭಾಷೆ", required = true)
    String grainageMasterNameInKannada;

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;

    @Schema(name = "username", example = "test")
    String username;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
