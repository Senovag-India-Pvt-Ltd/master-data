package com.sericulture.masterdata.model.api.dbtStatusCheck;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DbtStatusCheckResponse {

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

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
