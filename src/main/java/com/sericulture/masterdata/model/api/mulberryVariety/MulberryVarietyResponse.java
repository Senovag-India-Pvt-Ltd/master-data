package com.sericulture.masterdata.model.api.mulberryVariety;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MulberryVarietyResponse {

    @Schema(name="mulberryVarietyId", example = "1")
    int mulberryVarietyId;

    @Schema(name = "mulberryVarietyName", example = "Mulberry variety 1")
    String mulberryVarietyName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}