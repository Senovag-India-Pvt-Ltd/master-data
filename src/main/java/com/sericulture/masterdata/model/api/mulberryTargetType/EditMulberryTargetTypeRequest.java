package com.sericulture.masterdata.model.api.mulberryTargetType;

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
public class EditMulberryTargetTypeRequest extends RequestBody {

    @Schema(name = "mulberryTargetTypeId", example = "1")
    Long mulberryTargetTypeId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "MarketType must contain only letters and numbers")
    @Schema(name = "mulberryTargetTypeName", example = "Commercial Market", required = true)
    String mulberryTargetTypeName;

    @Schema(name = "mulberryTargetTypeNameInKannada", example = "ವಾಣಿಜ್ಯ ಮಾರುಕಟ್ಟೆ", required = true)
    String mulberryTargetTypeNameInKannada;

}
