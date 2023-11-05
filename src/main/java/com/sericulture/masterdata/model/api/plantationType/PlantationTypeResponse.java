package com.sericulture.masterdata.model.api.plantationType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlantationTypeResponse {
    @Schema(name="plantationTypeId", example = "1")
    int plantationTypeId;

    @Schema(name = "plantationTypeName", example = "Mulberry Silk")
    String plantationTypeName;
}
