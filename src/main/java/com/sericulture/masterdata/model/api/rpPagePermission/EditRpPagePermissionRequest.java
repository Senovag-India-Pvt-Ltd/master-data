package com.sericulture.masterdata.model.api.rpPagePermission;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "pageName", example = "seedAndDLF")
    String pageName;

    @Schema(name = "route", example = "/home/Chwaki")
    String route;

    @Schema(name = "isPage", example = "0")
    private Boolean isPage;
}