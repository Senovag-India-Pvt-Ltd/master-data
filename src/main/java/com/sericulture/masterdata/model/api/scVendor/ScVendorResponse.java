package com.sericulture.masterdata.model.api.scVendor;

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
public class ScVendorResponse {
    @Schema(name = "scVendorId", example = "1")
    Long scVendorId;

    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;

    @Schema(name = "type", example = "1")
    Long type;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
