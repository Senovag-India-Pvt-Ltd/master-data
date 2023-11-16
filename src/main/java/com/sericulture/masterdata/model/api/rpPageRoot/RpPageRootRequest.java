package com.sericulture.masterdata.model.api.rpPageRoot;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RpPageRootRequest extends RequestBody {
    @Schema(name = "rpPageRootName", example = "rpPageRoot 1 ", required = true)
    String rpPageRootName;
}
