package com.sericulture.masterdata.model.api.rpRoleAssociation;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RpRoleAssociationRequest extends RequestBody {

    @Schema(name = "roleId", example = "1", required = true)
    Long roleId;

    @Schema(name = "rpRolePermissionId", example = "1", required = true)
    Long rpRolePermissionId;
}
