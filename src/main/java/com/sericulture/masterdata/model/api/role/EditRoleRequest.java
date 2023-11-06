package com.sericulture.masterdata.model.api.role;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditRoleRequest extends RequestBody {
    @Schema(name = "roleId", example = "1")
    Integer roleId;

    @Schema(name = "roleName", example = "Role 1", required = true)
    String roleName;

}
