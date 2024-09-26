package com.sericulture.masterdata.model.api.hdQuestionMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdQuestionMasterRequest  extends RequestBody {
    @Schema(name = "hdQuestionId", example = "1")
    Long hdQuestionId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Question name must contain only letters and numbers")
    @Schema(name = "hdQuestionName", example = "Karnataka", required = true)
    String hdQuestionName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD question answer name must contain only letters and numbers")
    @Schema(name = "hdQuestionAnswerName", example = "Karnataka",required=true)
    String hdQuestionAnswerName;

    @Schema(name = "hdFaqUploadPath", example = "Karnataka", required = true)
    String hdFaqUploadPath;

}
