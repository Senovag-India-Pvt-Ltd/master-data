package com.sericulture.masterdata.model.api.scSubSchemeDetails;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditScSubSchemeDetailsRequest extends RequestBody {

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Long scSubSchemeDetailsId;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Sub Scheme name must contain only letters and numbers")
    @Schema(name = "subSchemeName", example = "Karnataka", required = true)
    String subSchemeName;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Sc Sub Scheme Name in kannada must contain only letters and numbers")
    @Schema(name = "subSchemeNameInKannada", example = "ಕನ್ನಡ")
    String subSchemeNameInKannada;

    @Schema(name = "subSchemeType", example = "1")
    Long subSchemeType;

    @Schema(name = "subSchemeStartDate", example = "1")
    Date subSchemeStartDate;

    @Schema(name = "subSchemeEndDate", example = "1")
    Date subSchemeEndDate;

    @Schema(name = "withLand", example = "1")
    Boolean withLand;

    @Schema(name = "beneficiaryType", example = "1")
    Long beneficiaryType;

    @Schema(name = "allowMultipleSanction", example = "1")
    Boolean allowMultipleSanction;

    @Schema(name = "sanctionForReeling", example = "1")
    Boolean sanctionForReeling;

//    @Schema(name = "sanctionOrderForScheme", example = "sanctionOrderForScheme")
//    String sanctionOrderForScheme;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DBT Code must contain only letters and numbers")
    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;

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

    @Schema(name = "sanctionEnable", example = "0 or 1")
    Integer sanctionEnable;

    @Schema(name = "schemeCodeForSanctionOrder", example = "calculation")
    String schemeCodeForSanctionOrder;

    @Schema(name = "monthlyFrequency", example = "1")
    Boolean monthlyFrequency;


}
