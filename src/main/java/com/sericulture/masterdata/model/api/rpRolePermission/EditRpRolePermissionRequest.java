package com.sericulture.masterdata.model.api.rpRolePermission;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "value", example = "1")
    String value;
}
