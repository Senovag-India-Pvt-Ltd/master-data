package com.sericulture.masterdata.model.api.hdBoardCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdBoardCategoryMasterRequest extends RequestBody {

    @Schema(name = "hdBoardCategoryId", example = "1")
    Integer hdBoardCategoryId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD board category name must contain only letters and numbers")
    @Schema(name = "hdBoardCategoryName", example = "Karnataka", required = true)
    String hdBoardCategoryName;
}
