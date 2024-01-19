package com.sericulture.masterdata.model.api.trInstitutionMaster;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrInstitutionMasterResponse {

    @Schema(name="trInstitutionMasterId", example = "1")
    int trInstitutionMasterId;

    @Schema(name = "trInstitutionMasterName", example = "Karnataka")
    String trInstitutionMasterName;

    @Schema(name = "trInstitutionNameInKannada", example = "ಕನ್ನಡ")
    String trInstitutionNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
