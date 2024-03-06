package com.sericulture.masterdata.model.api.tscMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditTscMasterRequest extends RequestBody {

    @Schema(name = "tscMasterId", example = "1")
    Long tscMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tsc Master name must contain only letters and numbers")
    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Tsc Master Name in kannada must contain only letters and numbers")
    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;
}
