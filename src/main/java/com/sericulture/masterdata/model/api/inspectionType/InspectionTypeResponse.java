package com.sericulture.masterdata.model.api.inspectionType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspectionTypeResponse {
    @Schema(name = "inspectionTypeId", example = "1")
    Long inspectionTypeId;

    @Schema(name = "name",  example = "Karnataka",required=true)
    String name;


    @Schema(name = "nameInKannada", example = "Karnataka",required=true)
    String nameInKannada;

    @Schema(name = "value", example = "1")
    Long value;

    @Schema(name = "version", example = "1")
    Long version;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}
