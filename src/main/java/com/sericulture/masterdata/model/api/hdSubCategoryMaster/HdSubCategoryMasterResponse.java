package com.sericulture.masterdata.model.api.hdSubCategoryMaster;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class HdSubCategoryMasterResponse {
    @Schema(name = "hdSubCategoryId", example = "1")
    Integer hdSubCategoryId;

    @Schema(name = "hdSubCategoryName", example = "Karnataka", required = true)
    String hdSubCategoryName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
