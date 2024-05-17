package com.sericulture.masterdata.model.api.rejectReasonWorkflowMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditRejectReasonWorkFlowMasterRequest extends RequestBody {

    @Schema(name = "rejectReasonWorkFlowMasterId", example = "1")
    Long rejectReasonWorkFlowMasterId;

    @Schema(name = "reason", example = "Karnataka", required = true)
    String reason;
}
