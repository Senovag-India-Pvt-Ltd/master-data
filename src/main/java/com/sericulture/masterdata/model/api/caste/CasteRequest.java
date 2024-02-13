package com.sericulture.masterdata.model.api.caste;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CasteRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Caste title must contain only letters and numbers")
    @Schema(name = "title", example = "GM", required = true)
    String title;

    @Schema(name = "code", example = "123", required = true)
    String code;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Caste name in kannada must contain only letters and numbers")
    @Schema(name = "nameInKannada", example = "ಭಾಷೆ")
    String nameInKannada;
}
