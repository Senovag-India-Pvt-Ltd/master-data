package com.sericulture.masterdata.model.api.trCourseMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TrCourseMasterRequest extends RequestBody {


    @Schema(name = "trCourseMasterName", example = "Karnataka", required = true)
    String trCourseMasterName;

    @Schema(name = "trCourseNameInKannada", example = "Karnataka", required = true)
    String trCourseNameInKannada;
}
