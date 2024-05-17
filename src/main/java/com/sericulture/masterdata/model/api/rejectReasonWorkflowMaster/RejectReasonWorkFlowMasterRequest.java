package com.sericulture.masterdata.model.api.rejectReasonWorkflowMaster;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RejectReasonWorkFlowMasterRequest {

    @Schema(name = "reason", example = "Karnataka", required = true)
    String reason;
}
