package com.sericulture.masterdata.model.api.trProgramMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TrProgramMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr program name must contain only letters and numbers")
    @Schema(name = "trProgramMasterName", example = "Karnataka", required = true)
    String trProgramMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Tr Program name in kannada must contain only letters and numbers")
    @Schema(name = "trProgramNameInKannada", example = "ಕನ್ನಡ")
    String trProgramNameInKannada;
}
