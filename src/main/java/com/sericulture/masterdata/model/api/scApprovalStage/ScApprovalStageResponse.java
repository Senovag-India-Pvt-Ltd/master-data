package com.sericulture.masterdata.model.api.scApprovalStage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScApprovalStageResponse {

    @Schema(name = "scApprovalStageId", example = "1")
    Long scApprovalStageId;

    @Schema(name = "stageName", example = "Karnataka", required = true)
    String stageName;

    @Schema(name = "stageNameInKannada", example = "ಕನ್ನಡ")
    String stageNameInKannada;

    @Schema(name = "workFlowType", example = "1")
    String workFlowType;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
