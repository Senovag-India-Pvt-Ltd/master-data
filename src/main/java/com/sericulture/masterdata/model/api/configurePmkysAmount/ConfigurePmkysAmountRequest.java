package com.sericulture.masterdata.model.api.configurePmkysAmount;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ConfigurePmkysAmountRequest extends RequestBody {

    @Schema(name = "spacingId", example = "1",required=true)
    Long spacingId;

    @Schema(name = "hectareId", example = "1",required=true)
    Long hectareId;

    @Schema(name = "amount", example = "2",required=true)
    Float amount;
}
