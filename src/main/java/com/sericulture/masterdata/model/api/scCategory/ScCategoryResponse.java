package com.sericulture.masterdata.model.api.scCategory;


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
public class ScCategoryResponse {

    @Schema(name = "scCategoryId", example = "1")
    Long scCategoryId;


    @Schema(name = "categoryName", example = "Karnataka", required = true)
    String categoryName;


    @Schema(name = "categoryNameInKannada", example = "ಕನ್ನಡ")
    String categoryNameInKannada;

    @Schema(name = "codeNumber", example = "Karnataka", required = true)
    String codeNumber;

    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;

    @Schema(name = "description", example = "Karnataka", required = true)
    String description;

    @Schema(name = "categoryShortName", example = "Karnataka", required = true)
    String categoryShortName;

    @Schema(name = "categoryCodeForSanctionOrder", example = "calculation")
    String categoryCodeForSanctionOrder;

    @Schema(name = "schemeId", example = "1")
    Long schemeId;

    @Schema(name = "subSchemeId", example = "1")
    Long subSchemeId;

    @Schema(name = "schemeName", example = "Scheme Name")
    String schemeName;

    @Schema(name = "subSchemeName", example = "Sub Scheme Name")
    String subSchemeName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
