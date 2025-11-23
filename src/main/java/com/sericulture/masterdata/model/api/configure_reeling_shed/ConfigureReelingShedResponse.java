package com.sericulture.masterdata.model.api.configure_reeling_shed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigureReelingShedResponse {

    @Schema(name = "reelingShedId", example = "1")
    Long reelingShedId;

    @Schema(name = "reelingUnit", example = "Unit A", required = true)
    String reelingUnit;

    @Schema(name = "reelingSqft", example = "1200")
    String reelingSqft;

    @Schema(name = "categoryId", example = "1")
    Long categoryId;

    @Schema(name = "componentId", example = "1")
    Long componentId;

    @Schema(name = "componentTypeId", example = "1")
    Long componentTypeId;

    @Schema(name = "machineTypeId", example = "1")
    Long machineTypeId;

    @Schema(name = "unitCost", example = "200.0")
    Float unitCost;

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

    @Schema(name = "machineTypeName", example = "120.5")
    String machineTypeName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;



}

