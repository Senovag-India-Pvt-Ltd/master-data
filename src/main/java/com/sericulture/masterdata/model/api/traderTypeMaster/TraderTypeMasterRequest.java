package com.sericulture.masterdata.model.api.traderTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Name;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TraderTypeMasterRequest extends RequestBody {
    @Schema(name = "traderTypeMasterName", example = "trader type 1", required = true)
    String traderTypeMasterName;

    @Schema(name = "traderTypeNameInKannada",  example = "ಭಾಷೆ")
    String traderTypeNameInKannada;

    @Schema(name = "noOfDeviceAllowed",  example = "1")
    Long noOfDeviceAllowed;
}
