package com.sericulture.masterdata.model.api.reelerTypeMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)

public class ReelerTypeMasterResponse {
    @Schema(name = "reelerTypeMasterId", example = "1")
    int reelerTypeMasterId;

    @Schema(name = "reelerTypeMasterName", example = "Karnataka", required = true)
    String reelerTypeMasterName;

    @Schema(name = "noOfDeviceAllowed", example = "3")
    private Long noOfDeviceAllowed;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
