package com.sericulture.masterdata.model.api.configureAdoptingBoiler;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditConfigureAdoptingBoilerRequest extends RequestBody {

    @Schema(name = "adoptingBoilerId", example = "1")
    Long adoptingBoilerId;

    @Schema(name = "boilerInKg", example = "Table A")
    Float boilerInKg;

    @Schema(name = "categoryId", example = "1")
    Long categoryId;

    @Schema(name = "componentId", example = "1")
    Long componentId;

    @Schema(name = "componentTypeId", example = "1")
    Long componentTypeId;

    @Schema(name = "unitCost", example = "150.0")
    Float unitCost;

    @Schema(name = "min", example = "120.5")
    Float min;

    @Schema(name = "max", example = "120.5")
    Float max;
}
