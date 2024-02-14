package com.sericulture.masterdata.model.api.hdBoardCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdBoardCategoryMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Board category name text must contain only letters and numbers")
    @Schema(name = "hdBoardCategoryName", example = "Karnataka", required = true)
    String hdBoardCategoryName;
}
