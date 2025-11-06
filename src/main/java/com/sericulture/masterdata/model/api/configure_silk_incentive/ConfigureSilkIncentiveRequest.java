package com.sericulture.masterdata.model.api.configure_silk_incentive;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ConfigureSilkIncentiveRequest extends RequestBody {

    @Schema(name = "machineTypeId", example = "1")
    Long machineTypeId;

    @Schema(name = "categoryId", example = "1")
    Long categoryId;

    @Schema(name = "componentId", example = "1")
    Long componentId;

    @Schema(name = "componentTypeId", example = "1")
    Long componentTypeId;

    @Schema(name = "amountPerKg", example = "50.5")
    Float amountPerKg;

    @Schema(name = "min", example = "120.5")
    Float min;

    @Schema(name = "max", example = "120.5")
    Float max;

    @Schema(name = "rendittaGrade", example = "120.5")
    String rendittaGrade;

    @Schema(name = "silkTableBasinEnds", example = "120.5")
    String silkTableBasinEnds;
}

