package com.sericulture.masterdata.model.api.hdBoardCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdBoardCategoryMasterRequest extends RequestBody {

    @Schema(name = "hdBoardCategoryId", example = "1")
    Integer hdBoardCategoryId;

    @Schema(name = "hdBoardCategoryName", example = "Karnataka", required = true)
    String hdBoardCategoryName;
}
