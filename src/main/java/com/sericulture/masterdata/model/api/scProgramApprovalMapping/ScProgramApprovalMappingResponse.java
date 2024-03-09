package com.sericulture.masterdata.model.api.scProgramApprovalMapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScProgramApprovalMappingResponse {

    @Schema(name = "scProgramApprovalMappingId", example = "1")
    Long scProgramApprovalMappingId;

    @Schema(name = "scProgramId", example = "1")
    Long scProgramId;

    @Schema(name = "scApprovalStageId", example = "1")
    Long scApprovalStageId;

    @Schema(name = "designationId", example = "1")
    Long designationId;

    @Schema(name = "orders", example = "1")
    Long orders;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
