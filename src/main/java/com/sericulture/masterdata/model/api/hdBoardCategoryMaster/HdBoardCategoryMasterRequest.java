package com.sericulture.masterdata.model.api.hdBoardCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdBoardCategoryMasterRequest extends RequestBody {
    @Schema(name = "hdBoardCategoryName", example = "Karnataka", required = true)
    String hdBoardCategoryName;
}
