package com.sericulture.masterdata.model.api.mulberryTargetType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MulberryTargetTypeRequest extends RequestBody {

    @Schema(name = "mulberryTargetTypeName", example = "Commercial Market", required = true)
    String mulberryTargetTypeName;

    @Schema(name = "mulberryTargetTypeNameInKannada", example = "ವಾಣಿಜ್ಯ ಮಾರುಕಟ್ಟೆ", required = true)
    String mulberryTargetTypeNameInKannada;

    @Schema(name = "mulberryRequired", example = "true", required = false)
    Boolean mulberryRequired;

    @Schema(name = "unit", example = "Commercial Market", required = true)
    String unit;
}
