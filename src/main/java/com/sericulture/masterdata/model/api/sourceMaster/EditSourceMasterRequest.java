package com.sericulture.masterdata.model.api.sourceMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditSourceMasterRequest extends RequestBody {

    @Schema(name = "sourceMasterId", example = "1")
    Integer sourceMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Source must contain only letters and numbers")
    @Schema(name = "sourceMasterName", example = "sourceName 1", required = true)
    String sourceMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Source in kannada must contain only letters and numbers")
    @Schema(name = "sourceNameInKannada",  example = "ಭಾಷೆ")
    String sourceNameInKannada;
}
