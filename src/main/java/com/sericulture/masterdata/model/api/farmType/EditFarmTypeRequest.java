package com.sericulture.masterdata.model.api.farmType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditFarmTypeRequest extends RequestBody {
    @Schema(name = "farmTypeId", example = "1")
    Long farmTypeId;

    @Schema(name = "farmId", example = "1")
    Long farmId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Farm must contain only letters and numbers")
    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Farm Type Name in kannada must contain only letters and numbers")
    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;




}
