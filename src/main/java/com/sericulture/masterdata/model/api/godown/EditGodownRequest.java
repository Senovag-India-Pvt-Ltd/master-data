package com.sericulture.masterdata.model.api.godown;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditGodownRequest extends RequestBody {

    @Schema(name = "godownId", example = "1")
    Integer godownId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Godown name must contain only letters and numbers")
    @Schema(name = "godownName", example = "Gowdown 1", required = true)
    String godownName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Godown name in kannada must contain only letters and numbers")
    @Schema(name = "godownNameInKannada",  example = "ಭಾಷೆ")
    String godownNameInKannada;

    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;
}