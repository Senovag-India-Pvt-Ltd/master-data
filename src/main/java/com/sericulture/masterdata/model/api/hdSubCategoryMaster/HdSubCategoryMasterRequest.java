package com.sericulture.masterdata.model.api.hdSubCategoryMaster;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class HdSubCategoryMasterRequest {
    @Schema(name = "hdSubCategoryName", example = "Karnataka", required = true)
    String hdSubCategoryName;

    @Schema(name = "hdCategoryId", example = "1")
    Long hdCategoryId;

    @Schema(name = "hdBoardCategoryId", example = "1")
    Long hdBoardCategoryId;
}
