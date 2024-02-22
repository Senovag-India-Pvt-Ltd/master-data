package com.sericulture.masterdata.model.api.state;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditStateRequest extends RequestBody {

    @Schema(name = "stateId", example = "1")
    Integer stateId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "State must contain only letters and numbers")
    @Schema(name = "stateName", example = "Karnataka", required = true)
    String stateName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "State in kannada must contain only letters and numbers")
    @Schema(name = "stateNameInKannada",  example = "ಭಾಷೆ")
    String stateNameInKannada;
}