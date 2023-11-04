package com.sericulture.masterdata.model.api.village;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditVillageRequest extends RequestBody {
    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Integer districtId;

    @Schema(name = "talukId", example = "1")
    Integer talukId;

    @Schema(name = "hobliId", example = "1")
    Integer hobliId;

    @Schema(name = "villageId", example = "1")
    Integer villageId;

    @Schema(name = "villageName", example = "Hodala", required = true)
    String villageName;
}