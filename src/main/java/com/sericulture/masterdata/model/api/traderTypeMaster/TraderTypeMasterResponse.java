package com.sericulture.masterdata.model.api.traderTypeMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraderTypeMasterResponse  {

    @Schema(name = "traderTypeMasterId", example = "1 ")
    int traderTypeMasterId;

    @Schema(name="traderTypeMasterName", example = "trader type 1")
    String traderTypeMasterName;
}
