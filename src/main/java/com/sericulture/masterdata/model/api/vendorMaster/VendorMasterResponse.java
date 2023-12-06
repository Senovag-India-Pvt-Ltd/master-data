package com.sericulture.masterdata.model.api.vendorMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorMasterResponse {
    @Schema(name = "vendorMasterId", example = "1")
    Integer vendorMasterId;

    @Schema(name = "vendorMasterName", example = "vendor 1")
    String vendorMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}