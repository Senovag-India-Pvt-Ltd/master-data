package com.sericulture.masterdata.model.api.scComponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScComponentResponse {

    @Schema(name = "scComponentId", example = "1")
    Integer scComponentId;

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Long scSubSchemeDetailsId;

    @Schema(name = "subSchemeName", example = "scComponentName 1 ")
    String subSchemeName;

    @Schema(name = "scComponentName", example = "scComponentName 1 ")
    String scComponentName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
