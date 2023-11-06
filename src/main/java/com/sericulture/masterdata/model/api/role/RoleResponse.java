package com.sericulture.masterdata.model.api.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleResponse {
    @Schema(name="roleId", example = "1")
    int roleId;

    @Schema(name = "roleName", example = "Role 1")
    String roleName;
}
