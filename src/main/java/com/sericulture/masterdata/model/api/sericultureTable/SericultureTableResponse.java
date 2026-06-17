package com.sericulture.masterdata.model.api.sericultureTable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SericultureTableResponse {

    @Schema(name = "sericultureTableId", example = "1")
    Long sericultureTableId;

    @Schema(name = "stepId", example = "1")
    Integer stepId;

    @Schema(name = "daysCount", example = "30")
    Integer daysCount;

    @Schema(name = "groupNo", example = "1")
    Integer groupNo;

    @Schema(name = "approvalStageName", example = "Stage Name")
    String approvalStageName;

    @Schema(name = "schemeId", example = "1")
    Long schemeId;

    @Schema(name = "subSchemeId", example = "1")
    Long subSchemeId;

    @Schema(name = "schemeName", example = "Scheme Name")
    String schemeName;

    @Schema(name = "subSchemeName", example = "Sub Scheme Name")
    String subSchemeName;

    @Schema(name = "error", example = "false")
    Boolean error;

    @Schema(name = "error_description", example = "Error description")
    String error_description;
}
