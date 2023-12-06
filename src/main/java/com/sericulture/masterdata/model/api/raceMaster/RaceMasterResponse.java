package com.sericulture.masterdata.model.api.raceMaster;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceMasterResponse {

    @Schema(name="raceMasterId", example = "1")
    int raceMasterId;

    @Schema(name = "raceMasterName", example = "raceName 1")
    String raceMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
