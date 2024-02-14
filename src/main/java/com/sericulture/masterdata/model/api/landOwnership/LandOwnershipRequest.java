package com.sericulture.masterdata.model.api.landOwnership;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LandOwnershipRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Land owner ship name must contain only letters and numbers")
    @Schema(name = "landOwnershipName", example = "Individual", required = true)
    String landOwnershipName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Land ownership name in kannada must contain only letters and numbers")
    @Schema(name = "landOwnershipNameInKannada",  example = "ಭಾಷೆ")
    String landOwnershipNameInKannada;
}
