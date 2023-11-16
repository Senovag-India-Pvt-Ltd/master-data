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
    int rpRoleAssociationId;

    @Schema(name = "roleId", example = "1")
    int roleId;

    @Schema(name = "rpPagePermission", example = "1")
    int rpPagePermission;
}
