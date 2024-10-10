package com.sericulture.masterdata.model.api.reasonMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReasonResponse {
    @Schema(name = "reasonId", example = "1")
    Long reasonId;

    @Schema(name = "name",  example = "Karnataka",required=true)
    String name;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
