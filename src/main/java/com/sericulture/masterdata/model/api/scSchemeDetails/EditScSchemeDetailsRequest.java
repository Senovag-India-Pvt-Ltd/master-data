package com.sericulture.masterdata.model.api.scSchemeDetails;

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
public class EditScSchemeDetailsRequest extends RequestBody {

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Scheme Name must contain only letters and numbers")
    @Schema(name = "schemeName", example = "Karnataka", required = true)
    String schemeName;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Scheme name in kannada must contain only letters and numbers")
    @Schema(name = "schemeNameInKannada", example = "ಕನ್ನಡ")
    String schemeNameInKannada;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DBT Code must contain only letters and numbers")
    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;

    @Schema(name = "schemeStartDate", example = "1")
    Date schemeStartDate;

    @Schema(name = "schemeEndDate", example = "1")
    Date schemeEndDate;

    @Schema(name = "spacing", example = "1")
    Boolean spacing;

    @Schema(name = "hectare", example = "1")
    Boolean hectare;


}
