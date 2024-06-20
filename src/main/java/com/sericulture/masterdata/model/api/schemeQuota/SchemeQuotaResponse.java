package com.sericulture.masterdata.model.api.schemeQuota;

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
public class SchemeQuotaResponse {

    @Schema(name = "schemeQuotaId", example = "1")
    Long schemeQuotaId;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Schema(name = "schemeQuotaName", example = "Karnataka")
    String schemeQuotaName;

    @Schema(name = "schemeQuotaType", example = "Karnataka")
    String schemeQuotaType;

    @Schema(name = "schemeQuotaCode", example = "1")
    String schemeQuotaCode;

    @Schema(name = "schemeQuotaPaymentType", example = "1")
    String schemeQuotaPaymentType;

    @Schema(name = "schemeName", example = "1")
    String schemeName;

    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;

    @Schema(name = "ddoCode", example = "Karnataka", required = true)
    String ddoCode;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
