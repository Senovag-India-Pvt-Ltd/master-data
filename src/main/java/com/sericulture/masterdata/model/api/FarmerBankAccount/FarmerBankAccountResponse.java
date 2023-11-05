package com.sericulture.masterdata.model.api.FarmerBankAccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class FarmerBankAccountResponse {

    @Schema(name="farmerBankAccountId", example = "1")
    int farmerBankAccountId;

    @Schema(name="farmerId", example = "1")
    int farmerId;

    @Schema(name = "farmerBankName", example = "SBI")
    String farmerBankName;

    @Schema(name = "farmerBankAccountNumber", example = "123")
    String farmerBankAccountNumber;

    @Schema(name = "farmerBankBranchName", example = "Bengaluru")
    String farmerBankBranchName;

    @Schema(name = "farmerBankIfscCode", example = "SBI0005463")
    String farmerBankIfscCode;
}