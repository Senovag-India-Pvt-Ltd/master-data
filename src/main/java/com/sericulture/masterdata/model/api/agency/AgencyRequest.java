package com.sericulture.masterdata.model.api.agency;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AgencyRequest extends RequestBody {


    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "agencyCode name must contain only letters and numbers")
    @Schema(name = "agencyCode", example = "agencyCode 1", required = true)
    String agencyCode;

     @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "agencyCode name must contain only letters and numbers")
    @Schema(name = "agencyBankAcNo",  example = "123456")
    String agencyBankAcNo;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "agencyIfscCode must contain only letters and numbers")
    @Schema(name = "agencyIfscCode", example = "agencyIfscCode 1", required = true)
    String agencyIfscCode;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "agencyDistrictCode must contain only letters and numbers")
    @Schema(name = "agencyDistrictCode", example = "agencyDistrictCode 1", required = true)
    String agencyDistrictCode;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "agencyTalukCode name must contain only letters and numbers")
    @Schema(name = "agencyTalukCode", example = "agencyTalukCode 1", required = true)
    String agencyTalukCode;


}
