package com.sericulture.masterdata.model.api.externalUnitType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalUnitTypeResponse {
    @Schema(name = "externalUnitTypeId", example = "1")
    Integer externalUnitTypeId;

    @Schema(name = "externalUnitTypeName", example = "external unit type 1")
    String externalUnitTypeName;

}
