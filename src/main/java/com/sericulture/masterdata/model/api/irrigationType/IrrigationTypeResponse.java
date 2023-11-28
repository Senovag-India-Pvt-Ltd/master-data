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
}
