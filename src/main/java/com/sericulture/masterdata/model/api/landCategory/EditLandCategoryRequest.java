package com.sericulture.masterdata.model.api.landCategory;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditLandCategoryRequest extends RequestBody {
    @Schema(name = "id", example = "1")
    Integer id;

    @Schema(name = "landCategoryName", example = "SF", required = true)
    String landCategoryName;

    @Schema(name = "landCategoryNameInKannada",  example = "ಭಾಷೆ")
    String landCategoryNameInKannada;
}
