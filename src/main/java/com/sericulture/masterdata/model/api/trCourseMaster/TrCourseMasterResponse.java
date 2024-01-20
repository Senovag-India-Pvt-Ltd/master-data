package com.sericulture.masterdata.model.api.trCourseMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrCourseMasterResponse {
    @Schema(name = "trCourseMasterId", example = "1")
    Integer trCourseMasterId;

    @Schema(name = "trCourseMasterName", example = "Karnataka", required = true)
    String trCourseMasterName;

    @Schema(name = "trCourseNameInKannada", example = "ಕನ್ನಡ")
    String trCourseNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
