package com.sericulture.masterdata.model.api.tsActivityMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditTsActivityMasterRequest extends RequestBody {

    @Schema(name = "tsActivityMasterId", example = "1")
    Long tsActivityMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Activity name must contain only letters and numbers")
    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Activity Name in kannada must contain only letters and numbers")
    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Activity Code must contain only letters and numbers")
    @Schema(name = "code", example = "Karnataka", required = true)
    String code;
}
