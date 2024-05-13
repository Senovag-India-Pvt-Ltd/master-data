package com.sericulture.masterdata.model.api.scComponent;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditScComponentRequest extends RequestBody {

    @Schema(name = "scComponentId", example = "1")
    Integer scComponentId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DBT Code must contain only letters and numbers")
    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "ScComponent must contain only letters and numbers")
    @Schema(name = "scComponentName", example = "scComponentName 1 ", required = true)
    String scComponentName;
}
