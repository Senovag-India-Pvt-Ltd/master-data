package com.sericulture.masterdata.model.api.mulberryVariety;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MulberryVarietyRequest extends RequestBody {

    @Schema(name = "mulberryVarietyName", example = "Mulberry variety 1", required = true)
    String mulberryVarietyName;
}