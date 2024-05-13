package com.sericulture.masterdata.model.api.scSchemeDetails;

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
public class ScSchemeDetailsResponse {
    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Schema(name = "schemeName", example = "Karnataka", required = true)
    String schemeName;

    @Schema(name = "schemeNameInKannada", example = "ಕನ್ನಡ")
    String schemeNameInKannada;

    @Schema(name = "schemeStartDate", example = "1")
    Date schemeStartDate;

    @Schema(name = "schemeEndDate", example = "1")
    Date schemeEndDate;

    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
