package com.sericulture.masterdata.model.api.scSchemeDetails;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScSchemeDetailsRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Scheme Name must contain only letters and numbers")
    @Schema(name = "schemeName", example = "Karnataka", required = true)
    String schemeName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Scheme name in kannada must contain only letters and numbers")
    @Schema(name = "schemeNameInKannada", example = "ಕನ್ನಡ")
    String schemeNameInKannada;

    @Schema(name = "schemeStartDate", example = "1")
    Date schemeStartDate;

    @Schema(name = "schemeEndDate", example = "1")
    Date schemeEndDate;

}