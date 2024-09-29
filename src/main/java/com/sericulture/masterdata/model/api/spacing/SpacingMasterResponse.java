package com.sericulture.masterdata.model.api.spacing;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpacingMasterResponse {

    @Schema(name = "spacingId", example = "1")
    Long spacingId;

    @Schema(name = "spacingName", example = "Karnataka", required = true)
    String spacingName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
