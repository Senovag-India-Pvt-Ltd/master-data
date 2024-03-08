package com.sericulture.masterdata.model.api.scApprovalStage;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScApprovalStageRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Approval name must contain only letters and numbers")
    @Schema(name = "stageName", example = "Karnataka", required = true)
    String stageName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Sc Approval name in kannada must contain only letters and numbers")
    @Schema(name = "stageNameInKannada", example = "ಕನ್ನಡ")
    String stageNameInKannada;
}
