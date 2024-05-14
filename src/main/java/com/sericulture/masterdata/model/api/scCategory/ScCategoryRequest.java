package com.sericulture.masterdata.model.api.scCategory;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScCategoryRequest extends RequestBody {

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Category name must contain only letters and numbers")
    @Schema(name = "categoryName", example = "Karnataka", required = true)
    String categoryName;



//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Sc Category name in kannada must contain only letters and numbers")
    @Schema(name = "categoryNameInKannada", example = "ಕನ್ನಡ")
    String categoryNameInKannada;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Code Number must contain only letters and numbers")
    @Schema(name = "codeNumber", example = "Karnataka", required = true)
    String codeNumber;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DBT Code must contain only letters and numbers")
    @Schema(name = "dbtCode", example = "Karnataka", required = true)
    String dbtCode;


//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Description must contain only letters and numbers")
    @Schema(name = "description", example = "Karnataka", required = true)
    String description;
}
