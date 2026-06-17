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
public class SericultureTableApprovalStageCheckboxResponse {

    @Schema(name = "scApprovalStageId", example = "1")
    Long scApprovalStageId;

    @Schema(name = "stageName", example = "Stage Name")
    String stageName;

    @Schema(name = "checked", example = "false")
    Boolean checked;

    @Schema(name = "sericultureTableId", example = "1")
    Long sericultureTableId;

    @Schema(name = "daysCount", example = "30")
    Integer daysCount;

    @Schema(name = "groupNo", example = "1")
    Integer groupNo;
}
