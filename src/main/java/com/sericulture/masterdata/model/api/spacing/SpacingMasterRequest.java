package com.sericulture.masterdata.model.api.spacing;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SpacingMasterRequest {

    @Schema(name = "spacingName", example = "Karnataka", required = true)
    String spacingName;

    @Schema(name = "metre", example = "Karnataka", required = true)
    String metre;

    @Schema(name = "length", example = "Karnataka", required = true)
    String length;

    @Schema(name = "breadth", example = "Karnataka", required = true)
    String breadth;
}
