package com.sericulture.masterdata.model.api.dbtStatusCheck;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditDbtStatusCheckRequest extends RequestBody {
    @Schema(name = "dbtStatusCheckId", example = "1")
    Long dbtStatusCheckId;

    @Schema(name = "deptCode", example = "1")
    Long deptCode;

    @Schema(name = "schemeId", example = "1")
    Long schemeId;

    @Schema(name = "componentTypeId", example = "1")
    Long componentTypeId;

    @Schema(name = "componentId", example = "1")
    Long componentId;

    @Schema(name = "subComponentId", example = "1")
    Long subComponentId;

    @Schema(name = "dbtScheme", example = "1")
    Long dbtScheme;

    @Schema(name = "username", example = "username", required = true)
    String username;

    @Schema(name = "password", example = "password", required = true)
    String password;
}
