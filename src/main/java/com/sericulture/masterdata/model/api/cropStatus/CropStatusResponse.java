package com.sericulture.masterdata.model.api.cropStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class CropStatusResponse {
    @Schema(name = "cropStatusId", example = "1")
    Long cropStatusId;

    @Schema(name = "name",  example = "Karnataka",required=true)
    String name;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
