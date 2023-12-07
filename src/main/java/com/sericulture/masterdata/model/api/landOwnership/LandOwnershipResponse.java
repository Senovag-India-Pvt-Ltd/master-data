package com.sericulture.masterdata.model.api.landOwnership;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LandOwnershipResponse {
    @Schema(name="landOwnershipId", example = "1")
    int landOwnershipId;

    @Schema(name = "landOwnershipName", example = "Individual")
    String landOwnershipName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
