package com.sericulture.masterdata.model.api.irrigationType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class IrrigationTypeResponse {

    @Schema(name = "irrigationTypeId", example = "1")
    Integer irrigationTypeId;

    @Schema(name = "irrigationTypeName", example = "Flood")
    String irrigationTypeName;

    @Schema(name = "irrigationTypeNameInKannada",  example = "ಭಾಷೆ")
    String irrigationTypeNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
