package com.sericulture.masterdata.model.api.farmType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class FarmTypeResponse {
    @Schema(name = "farmTypeId", example = "1")
    Long farmTypeId;

    @Schema(name = "farmId", example = "1")
    Long farmId;

    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;

    @Schema(name = "farmName", example = "Karnataka", required = true)
    String farmName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
