package com.sericulture.masterdata.model.api.irrigationType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class IrrigationTypeRequest extends RequestBody {

    @Schema(name = "irrigationTypeName", example = "Flood", required = true)
    String irrigationTypeName;
}
