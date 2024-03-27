package com.sericulture.masterdata.model.api.scVendorBank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScVendorBankResponse {
    @Schema(name = "scVendorBankId", example = "1")
    Long scVendorBankId;

    @Schema(name = "bankName", example = "Karnataka", required = true)
    String bankName;

    @Schema(name = "ifscCode",example = "Karnataka", required = true)
    String ifscCode;

    @Schema(name = "branch", example = "Karnataka", required = true)
    String branch;

    @Schema(name = "accountNumber", example = "Karnataka", required = true)
    String accountNumber;

    @Schema(name = "upi", example = "Karnataka", required = true)
    String upi;

    @Schema(name = "scVendorId", example = "1")
    Long scVendorId;

    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
