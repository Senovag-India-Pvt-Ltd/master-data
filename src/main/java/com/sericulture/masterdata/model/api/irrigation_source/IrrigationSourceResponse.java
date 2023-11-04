package com.sericulture.masterdata.model.api.irrigation_source;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IrrigationSourceResponse {
    @Schema(name="irrigationSourceId", example = "1")
    int irrigationSourceId;

    @Schema(name = "irrigationSourceName", example = "Rainfall")
    String irrigationSourceName;
}
