package com.sericulture.masterdata.model.api.landCategory;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class LandCategoryRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Land category name must contain only letters and numbers")
    @Schema(name = "landCategoryName", example = "SF", required = true)
    String landCategoryName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Land category name in kannada must contain only letters and numbers")
    @Schema(name = "landCategoryNameInKannada",  example = "ಭಾಷೆ")
    String landCategoryNameInKannada;

}
