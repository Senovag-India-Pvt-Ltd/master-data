package com.sericulture.masterdata.model.api.roofType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoofTypeResponse {
    @Schema(name="roofTypeId", example = "1")
    int roofTypeId;

    @Schema(name = "roofTypeName", example = "Hip Roof")
    String roofTypeName;
}
