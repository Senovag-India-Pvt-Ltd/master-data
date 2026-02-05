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

//    @Schema(name = "sanctionOrderForScheme", example = "calculation")
//    String sanctionOrderForScheme;

    @Schema(name = "allowMultipleSanction", example = "1")
    Boolean allowMultipleSanction;

    @Schema(name = "sanctionForReeling", example = "1")
    Boolean sanctionForReeling;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

    @Schema(name = "calculationBasedOn", example = "calculation")
    String calculationBasedOn;

    @Schema(name = "workOrderForScheme", example = "calculation")
    String workOrderForScheme;

    @Schema(name = "sanctionOrderForScheme", example = "calculation")
    String sanctionOrderForScheme;

    @Schema(name = "unitForScheme", example = "calculation")
    String unitForScheme;

    @Schema(name = "acknowledgementForScheme", example = "calculation")
    String acknowledgementForScheme;

    @Schema(name = "admGovtOrder", example = "calculation")
    String admGovtOrder;

    @Schema(name = "schemeCircularNo", example = "calculation")
    String schemeCircularNo;

    @Schema(name = "deptDelegationNo", example = "calculation")
    String deptDelegationNo;

    @Schema(name = "allotReleaseNo", example = "calculation")
    String allotReleaseNo;


    @Schema(name = "admGovtDate", example = "1")
    Date admGovtDate;

    @Schema(name = "schemeCircularDate", example = "1")
    Date schemeCircularDate;

    @Schema(name = "deptDelegationDate", example = "1")
    Date deptDelegationDate;

    @Schema(name = "allotReleaseDate", example = "1")
    Date allotReleaseDate;

    @Schema(name = "sanctionEnable", example = "true")
    Boolean sanctionEnable;

}
