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
}
