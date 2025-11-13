package com.sericulture.masterdata.model.api.silkExchange;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditSilkExchangeRequest extends RequestBody {
    @Schema(name = "silkExchangeId", example = "1")
    Long silkExchangeId;

    @Schema(name = "silkExchangeName", example = "Karnataka", required = true)
    String silkExchangeName;

    @Schema(name = "silkExchNameInKannada",  example = "ಭಾಷೆ")
    String silkExchNameInKannada;
}
