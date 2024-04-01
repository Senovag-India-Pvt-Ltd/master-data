package com.sericulture.masterdata.model.api.financialYearMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditFinancialYearMasterRequest extends RequestBody {

    @Schema(name = "financialYearMasterId", example = "1")
    Long financialYearMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "  Financial Year  must contain only letters and numbers")
    @Schema(name = "financialYear", example = "Karnataka", required = true)
    String financialYear;

    @Schema(name = "isDefault", example = "1")
    Long isDefault;
}
