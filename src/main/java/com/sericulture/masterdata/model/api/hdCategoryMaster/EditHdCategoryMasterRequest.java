package com.sericulture.masterdata.model.api.hdCategoryMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdCategoryMasterRequest extends RequestBody {
    @Schema(name = "hdCategoryId", example = "1")
    Integer hdCategoryId;

    @Schema(name = "hdCategoryName", example = "Karnataka", required = true)
    String hdCategoryName;
}
