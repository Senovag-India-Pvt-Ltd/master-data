package com.sericulture.masterdata.model.api.lineNameMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditLineNameMasterRequest extends RequestBody {
    @Schema(name = "lineNameId", example = "1")
    Long lineNameId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "LineNameMaster name must contain only letters and numbers")
    @Schema(name = "lineName", example = "Karnataka", required = true)
    String lineName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "LineNameMaster in kannada must contain only letters and numbers")
    @Schema(name = "lineNameInKannada", example = "ಭಾಷೆ", required = true)
    String lineNameInKannada;
}
