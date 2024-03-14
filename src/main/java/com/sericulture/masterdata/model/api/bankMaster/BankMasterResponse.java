package com.sericulture.masterdata.model.api.bankMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankMasterResponse {

    @Schema(name = "bankMasterId", example = "1")
    Long bankMasterId;

    @Schema(name = "bankName", example = "Karnataka", required = true)
    String bankName;

    @Schema(name = "bankNameInKannada", example = "ಕನ್ನಡ")
    String bankNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
