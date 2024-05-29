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
public class EditVillageRequest extends RequestBody {
    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Long districtId;

    @Schema(name = "talukId", example = "1")
    Long talukId;

    @Schema(name = "hobliId", example = "1")
    Long hobliId;

    @Schema(name = "villageId", example = "1")
    Long villageId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Village name must contain only letters and numbers")
    @Schema(name = "villageName", example = "Hodala", required = true)
    String villageName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Village name in kannada must contain only letters and numbers")
    @Schema(name = "villageNameInKannada",  example = "ಭಾಷೆ")
    String villageNameInKannada;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "lgVillage must contain only letters and numbers")
    @Schema(name = "lgVillage", example = "Shimoga")
    String lgVillage;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Village Code must contain only letters and numbers")
    @Schema(name = "villageCode", example = "1234")
    String villageCode;
}