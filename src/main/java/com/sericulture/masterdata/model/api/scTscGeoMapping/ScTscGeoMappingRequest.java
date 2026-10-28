package com.sericulture.masterdata.model.api.scTscGeoMapping;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ScTscGeoMappingRequest extends RequestBody {

    @Schema(name = "districtId", example = "1", required = true)
    Long districtId;

    @Schema(name = "talukId", example = "1", required = true)
    Long talukId;

    @Schema(name = "hobliId", example = "1")
    Long hobliId;

    @Schema(name = "villageId", example = "1")
    Long villageId;

    @Schema(name = "tscMasterId", example = "1", required = true)
    Long tscMasterId;

    @Schema(name = "officerUserId", example = "1")
    Long officerUserId;
}
