package com.sericulture.masterdata.model.api.mulberrySource;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MulberrySourceRequest extends RequestBody {

    @Schema(name = "mulberrySourceName", example = "Mulberry source 1", required = true)
    String mulberrySourceName;
}