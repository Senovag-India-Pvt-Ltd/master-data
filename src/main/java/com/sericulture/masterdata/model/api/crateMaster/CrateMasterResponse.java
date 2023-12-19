package com.sericulture.masterdata.model.api.crateMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrateMasterResponse {

    @Schema(name = "crateMasterId", example = "1")
    Integer crateMasterId;

    @Schema(name = "raceMasterId", example = "1")
    Integer raceMasterId;

    @Schema(name = "marketId", example = "1")
    Integer marketId;

    @Schema(name = "godownId", example = "1")
    Integer godownId;

    @Schema(name = "approxWeightPerCrate", example = "2")
    Integer approxWeightPerCrate;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
