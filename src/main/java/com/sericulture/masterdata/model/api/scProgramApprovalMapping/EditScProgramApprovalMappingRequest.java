package com.sericulture.masterdata.model.api.scProgramApprovalMapping;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditScProgramApprovalMappingRequest extends RequestBody {

    @Schema(name = "scProgramApprovalMappingId", example = "1")
    Long scProgramApprovalMappingId;

    @Schema(name = "scProgramId", example = "1")
    Long scProgramId;

    @Schema(name = "scApprovalStageId", example = "1")
    Long scApprovalStageId;

    @Schema(name = "designationId", example = "1")
    Long designationId;
}
