package com.sericulture.masterdata.model.api.scProgramAccountMapping;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScProgramAccountMappingResponse {

    @Schema(name = "scProgramAccountMappingId", example = "1")
    Long scProgramAccountMappingId;

    @Schema(name = "scProgramId", example = "1")
    Long scProgramId;

    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;

    @Schema(name = "scCategoryId", example = "1")
    Long scCategoryId;

    @Schema(name = "scProgramName", example = "1")
    String scProgramName;

    @Schema(name = "scHeadAccountName", example = "1")
    String scHeadAccountName;

    @Schema(name = "categoryName", example = "1")
    String categoryName;


    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
