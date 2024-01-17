package com.sericulture.masterdata.model.api.caste;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CasteRequest extends RequestBody {

    @Schema(name = "title", example = "GM", required = true)
    String title;

    @Schema(name = "code", example = "123", required = true)
    String code;

    @Schema(name = "nameInKannada", example = "ಭಾಷೆ")
    String nameInKannada;
}
