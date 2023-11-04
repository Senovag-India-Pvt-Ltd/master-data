package com.sericulture.masterdata.model.api.godown;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GodownRequest extends RequestBody {

    @Schema(name = "godownName", example = "Godown 1", required = true)
    String godownName;

    @Schema(name = "marketMasterId", example = "1", required = true)
    Long marketMasterId;
}