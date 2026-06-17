package com.sericulture.masterdata.model.api.govtAccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GovtAccountResponse {

    @Schema(name = "govtAccountId", example = "1")
    Long govtAccountId;

    @Schema(name = "govtAccountNumber", example = "1234567890")
    String govtAccountNumber;

    @Schema(name = "bankName", example = "State Bank of India")
    String bankName;

    @Schema(name = "branch", example = "Main Branch")
    String branch;

    @Schema(name = "ifscCode", example = "SBIN0001234")
    String ifscCode;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Govt account already exists")
    String error_description;
}