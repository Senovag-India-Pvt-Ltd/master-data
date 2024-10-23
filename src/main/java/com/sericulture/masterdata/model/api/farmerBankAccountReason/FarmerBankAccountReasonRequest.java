package com.sericulture.masterdata.model.api.farmerBankAccountReason;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FarmerBankAccountReasonRequest extends RequestBody {


    @Schema(name = "farmerBankAccountReason", example = "Karnataka", required = true)
    String farmerBankAccountReason;
}
