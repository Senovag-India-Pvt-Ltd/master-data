package com.sericulture.masterdata.model.api.mulberrySource;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditMulberrySourceRequest extends RequestBody {

    @Schema(name = "mulberrySourceId", example = "1")
    Integer mulberrySourceId;

    @Schema(name = "mulberrySourceName", example = "Mulberry source 1", required = true)
    String mulberrySourceName;

    @Schema(name = "mulberrySourceNameInKannada",  example = "ಭಾಷೆ")
    String mulberrySourceNameInKannada;
}