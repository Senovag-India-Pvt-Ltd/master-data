package com.sericulture.masterdata.model.api.govtAccount;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GovtAccountRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Govt account number must contain only letters and numbers")
    @Schema(name = "govtAccountNumber", example = "1234567890", required = true)
    String govtAccountNumber;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Bank name must contain only letters and numbers")
    @Schema(name = "bankName", example = "State Bank of India", required = true)
    String bankName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-]*$", message = "Branch must contain only letters, numbers and basic punctuation")
    @Schema(name = "branch", example = "Main Branch", required = true)
    String branch;

    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "IFSC code must be in valid format (e.g. SBIN0001234)")
    @Schema(name = "ifscCode", example = "SBIN0001234", required = true)
    String ifscCode;
}