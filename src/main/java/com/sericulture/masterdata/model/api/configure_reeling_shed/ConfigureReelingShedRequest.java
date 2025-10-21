package com.sericulture.masterdata.model.api.configure_reeling_shed;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ConfigureReelingShedRequest extends RequestBody {

    @Schema(name = "reelingUnit", example = "Unit A", required = true)
    String reelingUnit;

    @Schema(name = "sqft", example = "1200", required = true)
    String sqft;

    @Schema(name = "categoryId", example = "1")
    Long categoryId;

    @Schema(name = "componentId", example = "1")
    Long componentId;

    @Schema(name = "componentTypeId", example = "1")
    Long componentTypeId;

    @Schema(name = "unitCost", example = "200.0")
    Float unitCost;

    @Schema(name = "min", example = "120.5")
    Float min;

    @Schema(name = "max", example = "120.5")
    Float max;
}
