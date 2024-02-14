package com.sericulture.masterdata.model.api.hdCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdCategoryMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Hd category name must contain only letters and numbers")
    @Schema(name = "hdCategoryName", example = "Karnataka", required = true)
    String hdCategoryName;

    @Schema(name = "hdBoardCategoryId", example = "1")
    Long hdBoardCategoryId;

}
