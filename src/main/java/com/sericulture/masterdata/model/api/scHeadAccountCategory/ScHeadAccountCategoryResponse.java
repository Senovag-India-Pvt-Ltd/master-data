package com.sericulture.masterdata.model.api.scHeadAccountCategory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScHeadAccountCategoryResponse {

    @Schema(name = "scHeadAccountCategoryId", example = "1")
    Long scHeadAccountCategoryId;

    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;

    @Schema(name = "scCategoryId", example = "1")
    Long scCategoryId;

    @Schema(name = "scHeadAccountName", example = "Karnataka", required = true)
    String scHeadAccountName;

    @Schema(name = "categoryName", example = "Karnataka", required = true)
    String categoryName;


    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
