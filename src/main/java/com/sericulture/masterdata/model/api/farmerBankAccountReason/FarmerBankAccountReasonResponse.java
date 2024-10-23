package com.sericulture.masterdata.model.api.farmerBankAccountReason;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class FarmerBankAccountReasonResponse {
    @Schema(name = "farmerBankAccountReasonId", example = "1")
    Long farmerBankAccountReasonId;

    @Schema(name = "farmerBankAccountReason", example = "Karnataka", required = true)
    String farmerBankAccountReason;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
