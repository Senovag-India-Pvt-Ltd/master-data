package com.sericulture.masterdata.model.api.scComponent;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScComponentRequest extends RequestBody {

    @Schema(name = "scComponentName", example = "scComponentName 1 ", required = true)
    String scComponentName;
}
