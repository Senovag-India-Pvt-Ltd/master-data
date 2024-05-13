package com.sericulture.masterdata.model.api.departmentMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditDepartmentMasterRequest extends RequestBody {

    @Schema(name = "departmentId", example = "departmentId 1", required = true)
    Long departmentId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "deptCode name must contain only letters and numbers")
    @Schema(name = "departmentCode", example = "deptCode 1", required = true)
    String departmentCode;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "deptName name must contain only letters and numbers")
    @Schema(name = "departmentName", example = "deptName 1", required = true)
    String departmentName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "departmentNameInKannada name in kannada must contain only letters and numbers")
    @Schema(name = "departmentNameInKannada",  example = "ಭಾಷೆ")
    String departmentNameInKannada;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "departmentDetails name must contain only letters and numbers")
    @Schema(name = "departmentDetails", example = "departmentDetails 1", required = true)
    String departmentDetails;
}
