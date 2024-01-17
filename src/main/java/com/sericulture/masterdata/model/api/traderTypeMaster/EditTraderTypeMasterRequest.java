package com.sericulture.masterdata.model.api.traderTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditTraderTypeMasterRequest extends RequestBody {

    @Schema(name = "traderTypeMasterId", example = "1")
    Integer traderTypeMasterId;

    @Schema(name = "traderTypeMasterName", example = "trader type 1", required = true)
    String traderTypeMasterName;

    @Schema(name = "traderTypeNameInKannada",  example = "ಭಾಷೆ")
    String traderTypeNameInKannada;
}
