package com.sericulture.masterdata.model.api.traderTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
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
}
