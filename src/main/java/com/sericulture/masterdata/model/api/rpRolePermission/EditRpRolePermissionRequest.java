package com.sericulture.masterdata.model.api.rpRolePermission;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditRpRolePermissionRequest extends RequestBody {

    @Schema(name = "rpRolePermissionId", example = "1")
    Long rpRolePermissionId;

    @Schema(name = "type", example = "1")
    Long type;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "value must contain only letters and numbers")
    @Schema(name = "value", example = "1")
    String value;
}
