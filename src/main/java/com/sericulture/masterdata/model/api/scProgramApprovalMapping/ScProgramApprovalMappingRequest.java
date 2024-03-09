package com.sericulture.masterdata.model.api.scProgramApprovalMapping;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScProgramApprovalMappingRequest extends RequestBody {

    @Schema(name = "scProgramId", example = "1")
    Long scProgramId;

    @Schema(name = "scApprovalStageId", example = "1")
    Long scApprovalStageId;

    @Schema(name = "designationId", example = "1")
    Long designationId;

    @Schema(name = "orders", example = "1")
    Long orders;
}
