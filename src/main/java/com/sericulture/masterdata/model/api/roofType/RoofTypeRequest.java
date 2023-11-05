package com.sericulture.masterdata.model.api.roofType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RoofTypeRequest extends RequestBody {
    @Schema(name = "roofTypeName", example = "Hip Roof", required = true)
    String roofTypeName;
}
