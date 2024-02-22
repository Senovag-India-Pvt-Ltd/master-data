package com.sericulture.masterdata.model.api.trGroupMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class TrGroupMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "TrGroup must contain only letters and numbers")
    @Schema(name = "trGroupMasterName", example = "Karnataka", required = true)
    String trGroupMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "TrGroup in kannada must contain only letters and numbers")
    @Schema(name = "trGroupNameInKannada", example = "ಕನ್ನಡ")
    String trGroupNameInKannada;

}
