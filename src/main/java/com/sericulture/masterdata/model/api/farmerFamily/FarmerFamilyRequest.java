package com.sericulture.masterdata.model.api.farmerFamily;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FarmerFamilyRequest extends RequestBody {
    @Schema(name = "farmerId", example = "1", required = true)
    Long farmerId;

    @Schema(name = "relationshipId", example = "1", required = true)
    Long relationshipId;

    @Schema(name = "farmerFamilyName", example = "Latha", required = true)
    String farmerFamilyName;
}