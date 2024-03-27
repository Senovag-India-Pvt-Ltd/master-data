package com.sericulture.masterdata.model.api.scVendorBank;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScVendorBankRequest  extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "BankName must contain only letters and numbers")
    @Schema(name = "bankName", example = "Karnataka", required = true)
    String bankName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Ifsc Code must contain only letters and numbers")
    @Schema(name = "ifscCode",example = "Karnataka", required = true)
    String ifscCode;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Branch name must contain only letters and numbers")
    @Schema(name = "branch", example = "Karnataka", required = true)
    String branch;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Account Number name must contain only letters and numbers")
    @Schema(name = "accountNumber", example = "Karnataka", required = true)
    String accountNumber;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Upi name must contain only letters and numbers")
    @Schema(name = "upi", example = "Karnataka", required = true)
    String upi;

    @Schema(name = "scVendorId", example = "1")
    Long scVendorId;
}
