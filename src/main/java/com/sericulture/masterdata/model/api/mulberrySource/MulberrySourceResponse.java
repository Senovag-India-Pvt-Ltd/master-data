package com.sericulture.masterdata.model.api.mulberrySource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MulberrySourceResponse {

    @Schema(name="mulberrySourceId", example = "1")
    int mulberrySourceId;

    @Schema(name = "mulberrySourceName", example = "Mulberry source 1")
    String mulberrySourceName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}