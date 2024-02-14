package com.sericulture.masterdata.model.api.subsidy;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SubsidyRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Subsidy name must contain only letters and numbers")
    @Schema(name = "subsidyName", example = "Subsidy 1", required = true)
    String subsidyName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Subsidy name in kannada must contain only letters and numbers")
    @Schema(name = "subsidyNameInKannada",  example = "ಭಾಷೆ")
    String subsidyNameInKannada;
}
