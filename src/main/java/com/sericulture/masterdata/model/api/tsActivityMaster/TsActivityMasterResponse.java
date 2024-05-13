package com.sericulture.masterdata.model.api.tsActivityMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TsActivityMasterResponse {
    @Schema(name = "tsActivityMasterId", example = "1")
    Long tsActivityMasterId;

    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;

    @Schema(name = "code", example = "Karnataka", required = true)
    String code;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
