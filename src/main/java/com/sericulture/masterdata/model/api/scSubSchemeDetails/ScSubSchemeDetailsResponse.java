package com.sericulture.masterdata.model.api.scSubSchemeDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScSubSchemeDetailsResponse {

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Long scSubSchemeDetailsId;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Schema(name = "subSchemeName", example = "Karnataka", required = true)
    String subSchemeName;

    @Schema(name = "subSchemeNameInKannada", example = "ಕನ್ನಡ")
    String subSchemeNameInKannada;

    @Schema(name = "subSchemeType", example = "1")
    Long subSchemeType;

    @Schema(name = "subSchemeStartDate", example = "1")
    Date subSchemeStartDate;

    @Schema(name = "subSchemeEndDate", example = "1")
    Date subSchemeEndDate;

    @Schema(name = "schemeName", example = "Karnataka", required = true)
    String schemeName;

    @Schema(name = "withLand", example = "1")
    Boolean withLand;

    @Schema(name = "beneficiaryType", example = "1")
    Long beneficiaryType;

    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;


}
