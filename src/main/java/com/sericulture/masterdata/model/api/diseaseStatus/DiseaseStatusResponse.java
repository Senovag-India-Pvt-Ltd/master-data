package com.sericulture.masterdata.model.api.diseaseStatus;

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
public class DiseaseStatusResponse {
    @Schema(name = "diseaseStatusId", example = "1")
    Long diseaseStatusId;

    @Schema(name = "name",  example = "Karnataka",required=true)
    String name;

    @Schema(name = "description",  example = "Karnataka",required=true)
    String description;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
