package com.sericulture.masterdata.model.api.hdSubCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditHdSubCategoryMasterRequest extends RequestBody {

    @Schema(name = "hdSubCategoryId", example = "1")
    Integer hdSubCategoryId;

    @Schema(name = "hdSubCategoryName", example = "Karnataka", required = true)
    String hdSubCategoryName;
}
