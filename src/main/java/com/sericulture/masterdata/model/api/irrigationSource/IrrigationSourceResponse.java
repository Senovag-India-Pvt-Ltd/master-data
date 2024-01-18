package com.sericulture.masterdata.model.api.irrigationSource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class IrrigationSourceResponse {
    @Schema(name="irrigationSourceId", example = "1")
    int irrigationSourceId;

    @Schema(name = "irrigationSourceName", example = "Rainfall")
    String irrigationSourceName;

    @Schema(name = "irrigationSourceNameInKannada",  example = "ಭಾಷೆ")
    String irrigationSourceNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
