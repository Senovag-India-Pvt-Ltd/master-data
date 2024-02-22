package com.sericulture.masterdata.model.api.rpPagePermission;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditRpPagePermissionRequest extends RequestBody {
    @Schema(name = "rpPagePermissionId", example = "1")
    Long rpPagePermissionId;

    @Schema(name = "root", example = "1")
    Long root;

    @Schema(name = "parent", example = "0")
    Long parent;

    @Pattern(regexp = "^[a-zA-Z0-9\\s-]*$", message = "RpPagePermission must contain only letters and numbers")
    @Schema(name = "pageName", example = "seedAndDLF")
    String pageName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s/-]*$", message = "Route must contain only letters and numbers")
    @Schema(name = "route", example = "/home/Chwaki")
    String route;

    @Schema(name = "isPage", example = "0")
    private Boolean isPage;

    @Pattern(regexp = "^[a-zA-Z0-9_\\s]*$", message = "mapCode must contain only letters and numbers")
    @Schema(name = "mapCode", example = "farmerregistration")
    String mapCode;
}