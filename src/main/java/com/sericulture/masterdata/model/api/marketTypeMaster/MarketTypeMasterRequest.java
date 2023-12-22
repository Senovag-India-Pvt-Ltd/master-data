package com.sericulture.masterdata.model.api.marketTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MarketTypeMasterRequest extends RequestBody {

    @Schema(name = "marketTypeMasterName", example = "Commercial Market", required = true)
    String marketTypeMasterName;

    @Schema(name = "reelerFee", example = "2.4")
    BigDecimal reelerFee;

    @Schema(name = "farmerFee", example = "2.3")
    BigDecimal farmerFee;

    @Schema(name = "traderFee", example = "1.2")
    BigDecimal traderFee;

}