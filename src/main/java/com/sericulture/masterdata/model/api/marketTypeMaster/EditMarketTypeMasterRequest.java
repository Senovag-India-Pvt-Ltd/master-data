package com.sericulture.masterdata.model.api.marketTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditMarketTypeMasterRequest extends RequestBody {

    @Schema(name = "marketTypeMasterId", example = "1")
    Integer marketTypeMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "MarketType must contain only letters and numbers")
    @Schema(name = "marketTypeMasterName", example = "Commercial Market", required = true)
    String marketTypeMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "MarketType in kannada must contain only letters and numbers")
    @Schema(name = "marketTypeNameInKannada",  example = "ಭಾಷೆ")
    String marketTypeNameInKannada;

    @Schema(name = "reelerFee", example = "2.4")
    BigDecimal reelerFee;

    @Schema(name = "farmerFee", example = "2.3")
    BigDecimal farmerFee;

    @Schema(name = "traderFee", example = "1.2")
    BigDecimal traderFee;

}
