package com.sericulture.masterdata.model.api.trGroupMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrGroupMasterResponse {
    @Schema(name="trGroupMasterId", example = "1")
    int trGroupMasterId;

    @Schema(name = "trGroupMasterName", example = "Karnataka")
    String trGroupMasterName;

    @Schema(name = "trGroupNameInKannada", example = "Karnataka")
    String trGroupNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
