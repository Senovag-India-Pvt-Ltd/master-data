package com.sericulture.masterdata.model.api.soilType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoilTypeResponse {
    @Schema(name="soilTypeId", example = "1")
    int soilTypeId;

    @Schema(name = "soilTypeName", example = "Red Soil")
    String soilTypeName;

    @Schema(name = "soilTypeNameInKannada",  example = "ಭಾಷೆ")
    String soilTypeNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
