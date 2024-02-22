package com.sericulture.masterdata.model.api.trCourseMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditTrCourseMasterRequest extends RequestBody {

    @Schema(name = "trCourseMasterId", example = "1")
    Integer trCourseMasterId;


    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "TrCourse must contain only letters and numbers")
    @Schema(name = "trCourseMasterName", example = "Karnataka", required = true)
    String trCourseMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "TrCourse in kannada must contain only letters and numbers")
    @Schema(name = "trCourseNameInKannada", example = "ಕನ್ನಡ")
    String trCourseNameInKannada;
}
