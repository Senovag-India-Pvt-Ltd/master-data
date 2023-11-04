package com.sericulture.masterdata.model.api.irrigationSource;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class IrrigationSourceRequest extends RequestBody {
    @Schema(name = "irrigationSourceName", example = "Rainfall", required = true)
    String irrigationSourceName;
}
