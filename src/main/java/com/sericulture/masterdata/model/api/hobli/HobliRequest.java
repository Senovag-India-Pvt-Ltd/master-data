package com.sericulture.masterdata.model.api.hobli;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class HobliRequest extends RequestBody {
    @Schema(name = "stateId", example = "1", required = true)
    Long stateId;

    @Schema(name = "districtId", example = "1", required = true)
    Long districtId;

    @Schema(name = "talukId", example = "1", required = true)
    Long talukId;

    @Schema(name = "hobliName", example = "Kasaba", required = true)
    String hobliName;

    @Schema(name = "hobliNameInKannada",  example = "ಭಾಷೆ")
    String hobliNameInKannada;
}