package com.sericulture.masterdata.model.api.caste;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasteResponse {

    @Schema(name="id", example = "1")
    int id;

    @Schema(name = "title", example = "GM")
    String title;

    @Schema(name = "code", example = "123")
    String code;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}
