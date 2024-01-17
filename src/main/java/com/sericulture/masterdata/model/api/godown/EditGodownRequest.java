package com.sericulture.masterdata.model.api.godown;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditGodownRequest extends RequestBody {

    @Schema(name = "godownId", example = "1")
    Integer godownId;

    @Schema(name = "godownName", example = "Gowdown 1", required = true)
    String godownName;

    @Schema(name = "godownNameInKannada",  example = "ಭಾಷೆ")
    String godownNameInKannada;

    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;
}