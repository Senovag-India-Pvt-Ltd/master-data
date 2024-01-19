package com.sericulture.masterdata.model.api.trProgramMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrProgramMasterResponse {

    @Schema(name = "trProgramMasterId", example = "1")
    Integer trProgramMasterId;

    @Schema(name = "trProgramMasterName", example = "Karnataka", required = true)
    String trProgramMasterName;

    @Schema(name = "trProgramNameInKannada", example = "ಕನ್ನಡ")
    String trProgramNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}


