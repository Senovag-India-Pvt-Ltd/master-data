package com.sericulture.masterdata.model.api.godown;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GodownResponse {

    @Schema(name="godownId", example = "1")
    int godownId;

    @Schema(name = "godownName", example = "Godown 1")
    String godownName;

    @Schema(name = "godownNameInKannada",  example = "ಭಾಷೆ")
    String godownNameInKannada;

    @Schema(name="marketMasterId", example = "1")
    int marketMasterId;

    @Schema(name="marketMasterName", example = "1")
    String marketMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}