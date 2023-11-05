package com.sericulture.masterdata.model.api.machineTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditMachineTypeMasterRequest extends RequestBody {

    @Schema(name = "machineTypeId", example = "1")
    Integer machineTypeId;

    @Schema(name = "machineTypeName", example = "Machine type name 1", required = true)
    String machineTypeName;
}