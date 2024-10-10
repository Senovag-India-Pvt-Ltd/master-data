package com.sericulture.masterdata.model.api.scApprovalStage;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScApprovalStageRequest extends RequestBody {

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Approval name must contain only letters and numbers")
    @Schema(name = "stageName", example = "Karnataka", required = true)
    String stageName;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Sc Approval name in kannada must contain only letters and numbers")
    @Schema(name = "stageNameInKannada", example = "ಕನ್ನಡ")
    String stageNameInKannada;

    @Schema(name = "workFlowType", example = "1")
    String workFlowType;

    @Schema(name = "action", example = "true")
    String action;

    @Schema(name = "workOrder", example = "true")
    private Boolean workOrder;

    @Schema(name = "sanctionOrder", example = "true")
    private Boolean sanctionOrder;

    @Schema(name = "inspection", example = "true")
    private Boolean inspection;

    @Schema(name = "pushToDbt", example = "true")
    private Boolean pushToDbt;

    @Schema(name = "financialDelegation", example = "true")
    private Boolean financialDelegation;
}
