package com.sericulture.masterdata.model.api.hdQuestionMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdQuestionMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Question name must contain only letters and numbers")
    @Schema(name = "hdQuestionName", example = "Karnataka", required = true)
    String hdQuestionName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Question answer name must contain only letters and numbers")
    @Schema(name = "hdQuestionAnswerName", example = "Karnataka",required=true)
    String hdQuestionAnswerName;

    @Schema(name = "hdFaqUploadPath", example = "Karnataka", required = true)
    String hdFaqUploadPath;
}
