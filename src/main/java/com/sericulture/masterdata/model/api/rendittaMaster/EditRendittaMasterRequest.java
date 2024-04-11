package com.sericulture.masterdata.model.api.rendittaMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditRendittaMasterRequest extends RequestBody {
    @Schema(name = "rendittaMasterId", example = "1")
    Long rendittaMasterId;

    @Schema(name = "raceMasterId", example = "1")
    Long raceMasterId;

    @Schema(name = "value", example = "1")
    BigDecimal value;

}
