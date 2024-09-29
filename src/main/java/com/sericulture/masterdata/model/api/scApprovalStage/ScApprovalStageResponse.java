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

    @Schema(name = "action", example = "action")
    String action;

    @Schema(name = "inspection", example = "1")
    Boolean inspection;

    @Schema(name = "workOrder", example = "1")
    Boolean workOrder;

    @Schema(name = "sanctionOrder", example = "1")
    Boolean sanctionOrder;

    @Schema(name = "pushToDbt", example = "1")
    Boolean pushToDbt;

    @Schema(name = "financialDelegation", example = "1")
    Boolean financialDelegation;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
