package com.sericulture.masterdata.model.api.landCategory;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandCategoryResponse {
    @Schema(name="id", example = "1")
    int id;

    @Schema(name = "landCategoryName", example = "SF")
    String landCategoryName;

    @Schema(name = "landCategoryNameInKannada",  example = "ಭಾಷೆ")
    String landCategoryNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
