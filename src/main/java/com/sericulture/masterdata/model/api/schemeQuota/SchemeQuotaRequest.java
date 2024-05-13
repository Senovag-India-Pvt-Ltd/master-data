package com.sericulture.masterdata.model.api.schemeQuota;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SchemeQuotaRequest extends RequestBody {

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "schemeQuotaName must contain only letters and numbers")
    @Schema(name = "schemeQuotaName", example = "Karnataka", required = true)
    String schemeQuotaName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = " schemeQuotaType in kannada must contain only letters and numbers")
    @Schema(name = "schemeQuotaType", example = "Karnataka")
    String schemeQuotaType;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = " schemeQuotaType in kannada must contain only letters and numbers")
    @Schema(name = "schemeQuotaCode", example = "1")
    String schemeQuotaCode;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DBT Code must contain only letters and numbers")
    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = " schemeQuotaType in kannada must contain only letters and numbers")
    @Schema(name = "schemeQuotaPaymentType", example = "1")
    String schemeQuotaPaymentType;
}
