package com.sericulture.masterdata.model.api.machineTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MachineTypeMasterRequest extends RequestBody {

    @Schema(name = "machineTypeName", example = "Machine type", required = true)
    String machineTypeName;

    @Schema(name = "machineTypeNameInKannada",  example = "ಭಾಷೆ")
    String machineTypeNameInKannada;
}