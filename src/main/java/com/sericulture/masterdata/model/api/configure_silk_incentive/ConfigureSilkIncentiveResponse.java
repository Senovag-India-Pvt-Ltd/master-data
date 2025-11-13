package com.sericulture.masterdata.model.api.configure_silk_incentive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigureSilkIncentiveResponse {

    @Schema(name = "silkIncentiveId", example = "1")
    Long silkIncentiveId;

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

    @Schema(name = "categoryName", example = "Basin End A", required = true)
    String categoryName;

    @Schema(name = "scComponentName", example = "Basin End A", required = true)
    String scComponentName;

    @Schema(name = "subSchemeName", example = "Basin End A", required = true)
    String subSchemeName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

    @Schema(name = "rendittaGrade", example = "120.5")
    String rendittaGrade;

    @Schema(name = "silkTableBasinEnds", example = "120.5")
    String silkTableBasinEnds;

    @Schema(name = "machineTypeName", example = "Basin End A", required = true)
    String machineTypeName;
}
