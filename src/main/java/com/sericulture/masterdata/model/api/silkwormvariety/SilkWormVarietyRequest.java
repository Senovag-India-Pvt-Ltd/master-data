package com.sericulture.masterdata.model.api.silkwormvariety;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SilkWormVarietyRequest extends RequestBody {

    @Schema(name = "silkWormVarietyName", example = "Bombyx Mori", required = true)
    String silkWormVarietyName;

    @Schema(name = "silkWormVarietyNameInKannada",  example = "ಭಾಷೆ")
    String silkWormVarietyNameInKannada;
}