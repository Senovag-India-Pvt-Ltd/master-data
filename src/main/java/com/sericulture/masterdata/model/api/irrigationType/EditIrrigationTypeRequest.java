package com.sericulture.masterdata.model.api.irrigationType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditIrrigationTypeRequest extends RequestBody {

    @Schema(name = "irrigationTypeId", example = "1")
    Integer irrigationTypeId;

    @Schema(name = "irrigationTypeName", example = "Flood", required = true)
    String irrigationTypeName;
}
