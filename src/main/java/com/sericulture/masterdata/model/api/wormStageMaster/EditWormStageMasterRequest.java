package com.sericulture.masterdata.model.api.wormStageMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditWormStageMasterRequest extends RequestBody {

    @Schema(name = "wormStageMasterId", example = "1")
    Integer wormStageMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Worm Stage name must contain only letters and numbers")
    @Schema(name = "wormStageMasterName", example = "Karnataka", required = true)
    String wormStageMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Work Stage name in kannada must contain only letters and numbers")
    @Schema(name = "wormStageMasterNameInKannada",  example = "ಭಾಷೆ")
    String wormStageMasterNameInKannada;
}
