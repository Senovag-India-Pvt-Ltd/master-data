package com.sericulture.masterdata.model.api.farmMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class FarmMasterResponse {
    @Schema(name = "farmId", example = "1")
    Long farmId;

    @Schema(name = "farmName", example = "Karnataka", required = true)
    String farmName;

    @Schema(name = "farmNameInKannada", example = "ಭಾಷೆ", required = true)
    String farmNameInKannada;

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;

    @Schema(name = "username", example = "test")
    String username;

    @Schema(name = "isBsf", example = "yes")
    String isBsf;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}
