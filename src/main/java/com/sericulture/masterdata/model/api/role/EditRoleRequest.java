package com.sericulture.masterdata.model.api.role;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditRoleRequest extends RequestBody {
    @Schema(name = "roleId", example = "1")
    Integer roleId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "tRole must contain only letters and numbers")
    @Schema(name = "roleName", example = "Role 1", required = true)
    String roleName;

}
