package com.sericulture.masterdata.model.api.bankMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BankMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Bank name must contain only letters and numbers")
    @Schema(name = "bankName", example = "Karnataka", required = true)
    String bankName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Bank name in kannada must contain only letters and numbers")
    @Schema(name = "bankNameInKannada", example = "ಕನ್ನಡ")
    String bankNameInKannada;
}
