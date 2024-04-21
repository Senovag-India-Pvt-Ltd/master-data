package com.sericulture.masterdata.model.api.scSubSchemeMapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScSubSchemeMappingResponse {

    @Schema(name = "scSubSchemeMappingId", example = "1")
    Long scSubSchemeMappingId;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Long scSubSchemeDetailsId;

    @Schema(name = "schemeName", example = "Karnataka", required = true)
    String schemeName;

    @Schema(name = "subSchemeName", example = "Karnataka", required = true)
    String subSchemeName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}
