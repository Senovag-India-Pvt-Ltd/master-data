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
}
