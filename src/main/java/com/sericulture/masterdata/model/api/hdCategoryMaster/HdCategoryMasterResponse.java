package com.sericulture.masterdata.model.api.hdCategoryMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class HdCategoryMasterResponse {
    @Schema(name = "hdCategoryId", example = "1")
    Integer hdCategoryId;

    @Schema(name = "hdCategoryName", example = "Karnataka", required = true)
    String hdCategoryName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
