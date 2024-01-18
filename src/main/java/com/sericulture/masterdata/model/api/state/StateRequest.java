package com.sericulture.masterdata.model.api.state;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StateRequest extends RequestBody {

    @Schema(name = "stateName", example = "Karnataka", required = true)
    String stateName;

    @Schema(name = "stateNameInKannada",  example = "ಭಾಷೆ")
    String stateNameInKannada;
}