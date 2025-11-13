package com.sericulture.masterdata.model.api.silkExchange;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SilkExchangeRequest extends RequestBody {



    @Schema(name = "silkExchangeName", example = "Karnataka", required = true)
    String silkExchangeName;

    @Schema(name = "silkExchNameInKannada",  example = "ಭಾಷೆ")
    String silkExchNameInKannada;
}
