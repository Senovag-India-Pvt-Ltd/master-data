package com.sericulture.masterdata.model.api.departmentMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentMasterResponse {

    @Schema(name = "departmentId", example = "departmentId 1", required = true)
    Long departmentId;

    @Schema(name = "departmentCode", example = "deptCode 1", required = true)
    String departmentCode;

    @Schema(name = "departmentName", example = "deptName 1", required = true)
    String departmentName;

    @Schema(name = "departmentNameInKannada",  example = "ಭಾಷೆ")
    String departmentNameInKannada;

    @Schema(name = "departmentDetails", example = "departmentDetails 1", required = true)
    String departmentDetails;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
