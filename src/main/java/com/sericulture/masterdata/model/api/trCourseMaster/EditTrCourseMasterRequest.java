package com.sericulture.masterdata.model.api.trCourseMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditTrCourseMasterRequest extends RequestBody {

    @Schema(name = "trCourseMasterId", example = "1")
    Integer trCourseMasterId;

    @Schema(name = "trCourseMasterName", example = "Karnataka", required = true)
    String trCourseMasterName;
}
