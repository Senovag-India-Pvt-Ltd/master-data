package com.sericulture.masterdata.model.api.releerTypeMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)

public class ReleerTypeMasterResponse {
    @Schema(name = "releerTypeMasterId", example = "1")
    int releerTypeMasterId;

    @Schema(name = "releerTypeMasterName", example = "Karnataka", required = true)
    String releerTypeMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
