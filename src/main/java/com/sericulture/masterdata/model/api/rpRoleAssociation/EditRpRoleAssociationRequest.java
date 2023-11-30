package com.sericulture.masterdata.model.api.rpRoleAssociation;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditRpRoleAssociationRequest extends RequestBody {

    @Schema(name = "rpRoleAssociationId", example = "1")
    Long rpRoleAssociationId;

    @Schema(name = "roleId", example = "1")
    Long roleId;

    @Schema(name = "rpRolePermissionId", example = "1")
    Long rpRolePermissionId;

}
