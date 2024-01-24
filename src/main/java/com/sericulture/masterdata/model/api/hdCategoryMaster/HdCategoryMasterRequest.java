package com.sericulture.masterdata.model.api.hdCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdCategoryMasterRequest extends RequestBody {
    @Schema(name = "hdCategoryName", example = "Karnataka", required = true)
    String hdCategoryName;

    @Schema(name = "hdBoardCategoryId", example = "1")
    Long hdBoardCategoryId;

}
