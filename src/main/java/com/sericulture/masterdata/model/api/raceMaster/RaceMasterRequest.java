package com.sericulture.masterdata.model.api.raceMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RaceMasterRequest extends RequestBody {

    @Schema(name = "raceMasterName", example = "raceName 1", required = true)
    String raceMasterName;

    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;
}
