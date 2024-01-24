package com.sericulture.masterdata.model.api.hdBoardCategoryMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class HdBoardCategoryMasterResponse {
    @Schema(name = "hdBoardCategoryId", example = "1")
    Integer hdBoardCategoryId;

    @Schema(name = "hdBoardCategoryName", example = "Karnataka", required = true)
    String hdBoardCategoryName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
