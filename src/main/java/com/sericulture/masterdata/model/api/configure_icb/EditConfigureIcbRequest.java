package com.sericulture.masterdata.model.api.configure_icb;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditConfigureIcbRequest extends RequestBody {

    @Schema(name = "icbId", example = "1")
    Long icbId;

    @Schema(name = "icbBasinEnds", example = "Basin End A", required = true)
    String icbBasinEnds;

    @Schema(name = "categoryId", example = "1")
    Long categoryId;

    @Schema(name = "componentId", example = "1")
    Long componentId;

    @Schema(name = "componentTypeId", example = "1")
    Long componentTypeId;

    @Schema(name = "unitCost", example = "120.5")
    Float unitCost;

    @Schema(name = "min", example = "120.5")
    Float min;

    @Schema(name = "max", example = "120.5")
    Float max;
}

