package com.sericulture.masterdata.model.api.trProgramMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditTrProgramMasterRequest extends RequestBody {

    @Schema(name = "trProgramMasterId", example = "1")
    Integer trProgramMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr Program name must contain only letters and numbers")
    @Schema(name = "trProgramMasterName", example = "Karnataka", required = true)
    String trProgramMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Tr Program name in kannada must contain only letters and numbers")
    @Schema(name = "trProgramNameInKannada", example = "ಕನ್ನಡ")
    String trProgramNameInKannada;
}
