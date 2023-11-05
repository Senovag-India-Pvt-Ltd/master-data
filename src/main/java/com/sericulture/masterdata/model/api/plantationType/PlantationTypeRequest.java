package com.sericulture.masterdata.model.api.plantationType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PlantationTypeRequest extends RequestBody {
    @Schema(name = "plantationTypeName", example = "Mulberry Silk", required = true)
    String plantationTypeName;
}
