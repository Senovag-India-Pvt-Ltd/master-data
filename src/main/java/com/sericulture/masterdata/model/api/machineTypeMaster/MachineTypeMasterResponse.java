package com.sericulture.masterdata.model.api.machineTypeMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineTypeMasterResponse {

    @Schema(name="machineTypeId", example = "1")
    int machineTypeId;

    @Schema(name = "machineTypeName", example = "Machine type 1")
    String machineTypeName;
}