package com.sericulture.masterdata.model.api.scComponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScComponentResponse {

    @Schema(name = "scComponentId", example = "1")
    Integer scComponentId;

    @Schema(name = "scComponentName", example = "scComponentName 1 ")
    String scComponentName;
}
