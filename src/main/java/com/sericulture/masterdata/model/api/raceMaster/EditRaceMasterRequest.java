package com.sericulture.masterdata.model.api.raceMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditRaceMasterRequest extends RequestBody {

    @Schema(name = "raceMasterId", example = "1")
    Integer raceMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "RaceMaster must contain only letters and numbers")
    @Schema(name = "raceMasterName", example = "raceName 1", required = true)
    String raceMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "RaceMaster in kannada must contain only letters and numbers")
    @Schema(name = "raceNameInKannada",  example = "ಭಾಷೆ")
    String raceNameInKannada;

    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;
}
