package com.sericulture.masterdata.model.api.wormStageMaster;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WormStageMasterResponse {
    @Schema(name = "wormStageMasterId", example = "1")
    Integer wormStageMasterId;

    @Schema(name = "wormStageMasterName", example = "Karnataka", required = true)
    String wormStageMasterName;

    @Schema(name = "wormStageMasterNameInKannada",  example = "ಭಾಷೆ")
    String wormStageMasterNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
