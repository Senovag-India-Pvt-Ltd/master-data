package com.sericulture.masterdata.model.api.hdQuestionMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdQuestionMasterRequest extends RequestBody {
    @Schema(name = "hdQuestionName", example = "Karnataka", required = true)
    String hdQuestionName;

    @Schema(name = "hdQuestionAnswerName", example = "Karnataka",required=true)
    String hdQuestionAnswerName;

    @Schema(name = "hdFaqUploadPath", example = "Karnataka", required = true)
    String hdFaqUploadPath;
}
