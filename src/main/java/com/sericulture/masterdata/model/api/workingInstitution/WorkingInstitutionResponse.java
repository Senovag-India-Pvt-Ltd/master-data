package com.sericulture.masterdata.model.api.workingInstitution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkingInstitutionResponse {

    @Schema(name = "workingInstitutionId", example = "1")
    int workingInstitutionId;

    @Schema(name = "workingInstituitonName", example = "Karnataka", required = true)
    String workingInstitutionName;

    @Schema(name = "workingInstitutionNameInKannada",  example = "ಭಾಷೆ")
    String workingInstitutionNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
