package com.sericulture.masterdata.model.api.education;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sericulture.masterdata.model.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class EducationResponse extends BaseResponse {

    @Schema(name="id", example = "1")
    int id;
    @Schema(name = "name", example = "Bachelor of Engineering")
    String name;
    @Schema(name = "code", example = "BE")
    String code;

    @Schema(name = "educationNameInKannada", example = "ಭಾಷೆ")
    String educationNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
