package com.sericulture.masterdata.model.api.scApprovalStage;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditScApprovalStageRequest extends RequestBody {

    @Schema(name = "scApprovalStageId", example = "1")
    Long scApprovalStageId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Approval name must contain only letters and numbers")
    @Schema(name = "stageName", example = "Karnataka", required = true)
    String stageName;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Sc Approval name in kannada must contain only letters and numbers")
    @Schema(name = "stageNameInKannada", example = "ಕನ್ನಡ")
    String stageNameInKannada;

    @Schema(name = "workFlowType", example = "1")
    String workFlowType;

    @Schema(name = "action", example = "true")
    String action;

}
