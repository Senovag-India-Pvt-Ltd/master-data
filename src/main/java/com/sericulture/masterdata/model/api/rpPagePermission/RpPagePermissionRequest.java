package com.sericulture.masterdata.model.api.rpPagePermission;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RpPagePermissionRequest extends RequestBody {
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
