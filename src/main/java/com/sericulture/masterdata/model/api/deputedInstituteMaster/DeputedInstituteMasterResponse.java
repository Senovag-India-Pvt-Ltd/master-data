package com.sericulture.masterdata.model.api.deputedInstituteMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeputedInstituteMasterResponse {

    @Schema(name = "deputedInstituteId", example = "1")
    Integer deputedInstituteId;

    @Schema(name = "deputedInstituteName", example = "external unit type 1", required = true)
    String deputedInstituteName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
