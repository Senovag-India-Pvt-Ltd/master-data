package com.sericulture.masterdata.model.api.farmerBankAccountReason;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditFarmerBankAccountReasonRequest extends RequestBody {
    @Schema(name = "farmerBankAccountReasonId", example = "1")
    Long farmerBankAccountReasonId;

    @Schema(name = "farmerBankAccountReason", example = "Karnataka", required = true)
    String farmerBankAccountReason;
}
