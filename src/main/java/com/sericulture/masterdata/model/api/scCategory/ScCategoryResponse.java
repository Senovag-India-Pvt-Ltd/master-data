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

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Category number must contain only letters and numbers")
    @Schema(name = "categoryNumber", example = "Karnataka", required = true)
    String categoryNumber;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Category name must contain only letters and numbers")
    @Schema(name = "categoryName", example = "Karnataka", required = true)
    String categoryName;


    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Sc Category name in kannada must contain only letters and numbers")
    @Schema(name = "categoryNameInKannada", example = "ಕನ್ನಡ")
    String categoryNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
