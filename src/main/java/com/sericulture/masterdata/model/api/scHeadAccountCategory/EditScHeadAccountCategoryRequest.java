package com.sericulture.masterdata.model.api.scHeadAccountCategory;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditScHeadAccountCategoryRequest  extends RequestBody {

    @Schema(name = "scHeadAccountCategoryId", example = "1")
    Long scHeadAccountCategoryId;

    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;

    @Schema(name = "scCategoryId", example = "1")
    Long scCategoryId;


}
