package com.sericulture.masterdata.model.api.village;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class VillageRequest extends RequestBody {
    @Schema(name = "stateId", example = "1", required = true)
    Long stateId;

    @Schema(name = "districtId", example = "1", required = true)
    Long districtId;

    @Schema(name = "talukId", example = "1", required = true)
    Long talukId;

    @Schema(name = "hobliId", example = "1", required = true)
    Long hobliId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Village must contain only letters and numbers")
    @Schema(name = "villageName", example = "Hodala", required = true)
    String villageName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Village name in kannada must contain only letters and numbers")
    @Schema(name = "villageNameInKannada",  example = "ಭಾಷೆ")
    String villageNameInKannada;
}