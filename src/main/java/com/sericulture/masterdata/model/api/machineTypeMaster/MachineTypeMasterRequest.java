package com.sericulture.masterdata.model.api.machineTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MachineTypeMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "MachineType must contain only letters and numbers")
    @Schema(name = "machineTypeName", example = "Machine type", required = true)
    String machineTypeName;


    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "MachineType name in kannada must contain only letters and numbers")
    @Schema(name = "machineTypeNameInKannada",  example = "ಭಾಷೆ")
    String machineTypeNameInKannada;
}