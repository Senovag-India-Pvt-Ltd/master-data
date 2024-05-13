package com.sericulture.masterdata.model.api.scVendor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScVendorResponse {
    @Schema(name = "scVendorId", example = "1")
    Long scVendorId;

    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;

    @Schema(name = "type", example = "1")
    Long type;

    @Schema(name = "agencyCode", example = "agencyCode 1", required = true)
    String agencyCode;

    @Schema(name = "agencyBankAcNo",  example = "123456")
    String agencyBankAcNo;

    @Schema(name = "agencyIfscCode", example = "agencyIfscCode 1", required = true)
    String agencyIfscCode;

    @Schema(name = "agencyDistrictCode", example = "agencyDistrictCode 1", required = true)
    String agencyDistrictCode;

    @Schema(name = "agencyTalukCode", example = "agencyTalukCode 1", required = true)
    String agencyTalukCode;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
