package com.sericulture.masterdata.model.api.landOwnership;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditLandOwnershipRequest extends RequestBody {
    @Schema(name = "landOwnershipId", example = "1")
    Integer landOwnershipId;

    @Schema(name = "landOwnershipName", example = "Individual", required = true)
    String landOwnershipName;

    @Schema(name = "landOwnershipNameInKannada",  example = "ಭಾಷೆ")
    String landOwnershipNameInKannada;
}
