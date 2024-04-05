package com.sericulture.masterdata.model.api.scSubSchemeDetails;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditScSubSchemeDetailsRequest extends RequestBody {

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Long scSubSchemeDetailsId;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Sub Scheme name must contain only letters and numbers")
    @Schema(name = "subSchemeName", example = "Karnataka", required = true)
    String subSchemeName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Sc Sub Scheme Name in kannada must contain only letters and numbers")
    @Schema(name = "subSchemeNameInKannada", example = "ಕನ್ನಡ")
    String subSchemeNameInKannada;

    @Schema(name = "subSchemeType", example = "1")
    Long subSchemeType;

    @Schema(name = "subSchemeStartDate", example = "1")
    Date subSchemeStartDate;

    @Schema(name = "subSchemeEndDate", example = "1")
    Date subSchemeEndDate;


}