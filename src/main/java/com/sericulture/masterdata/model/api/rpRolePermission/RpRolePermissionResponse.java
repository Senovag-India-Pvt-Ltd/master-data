package com.sericulture.masterdata.model.api.rpRolePermission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RpRolePermissionResponse {

    @Schema(name = "rpRolePermissionId", example = "1")
    int rpRolePermissionId;

    @Schema(name = "type", example = "1")
    int type;

    @Schema(name = "value", example = "1")
    String value;
}
