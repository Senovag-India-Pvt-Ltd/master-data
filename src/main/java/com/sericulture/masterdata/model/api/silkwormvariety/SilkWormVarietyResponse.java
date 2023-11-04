package com.sericulture.masterdata.model.api.silkwormvariety;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SilkWormVarietyResponse {

    @Schema(name="silkWormVarietyId", example = "1")
    int silkWormVarietyId;

    @Schema(name = "silkWormVarietyName", example = "Bombyx Mori")
    String silkWormVarietyName;
}