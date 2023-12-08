package com.sericulture.masterdata.model.api.rpRoleAssociation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RpRoleAssociationResponse {

    @Schema(name = "rpRoleAssociationId", example = "1")
    Long rpRoleAssociationId;

    @Schema(name = "roleId", example = "1")
    Long roleId;

    @Schema(name = "rpRolePermissionId", example = "1")
    Long rpRolePermissionId;

    @Schema(name = "value", example = "1")
    Long value;

    @Schema(name = "mapCode", example = "mapCode value")
    String mapCode;

    @Schema(name = "success", example = "1")
    Boolean success;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
