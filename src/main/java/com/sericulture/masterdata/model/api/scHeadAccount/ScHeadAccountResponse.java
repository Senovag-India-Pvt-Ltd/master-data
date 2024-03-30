package com.sericulture.masterdata.model.api.scHeadAccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScHeadAccountResponse {

    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;

    @Schema(name = "scHeadAccountName", example = "scHeadAccount 1 ")
    String scHeadAccountName;

    @Schema(name = "scHeadAccountNameInKannada",  example = "ಭಾಷೆ")
    String scHeadAccountNameInKannada;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Schema(name = "schemeName", example = "Karnataka", required = true)
    String schemeName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
