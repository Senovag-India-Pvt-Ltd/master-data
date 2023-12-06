package com.sericulture.masterdata.model.api.rpPagePermission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RpPagePermissionResponse {
    @Schema(name = "rpPagePermissionId", example = "1")
    Long rpPagePermissionId;

    @Schema(name = "root", example = "1")
    Long root;

    @Schema(name = "parent", example = "0")
    Long parent;

    @Schema(name = "pageName", example = "seedAndDLF")
    String pageName;

    @Schema(name = "route", example = "/home/Chwaki")
    String route;

    @Schema(name = "isPage", example = "0")
    private Boolean isPage;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
