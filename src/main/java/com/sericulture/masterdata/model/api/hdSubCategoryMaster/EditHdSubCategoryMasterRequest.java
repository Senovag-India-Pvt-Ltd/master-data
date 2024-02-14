package com.sericulture.masterdata.model.api.hdSubCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditHdSubCategoryMasterRequest extends RequestBody {

    @Schema(name = "hdSubCategoryId", example = "1")
    Long hdSubCategoryId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Sub category name must contain only letters and numbers")
    @Schema(name = "hdSubCategoryName", example = "Karnataka", required = true)
    String hdSubCategoryName;

    @Schema(name = "hdCategoryId", example = "1")
    Long hdCategoryId;

    @Schema(name = "hdBoardCategoryId", example = "1")
    Long hdBoardCategoryId;

}
