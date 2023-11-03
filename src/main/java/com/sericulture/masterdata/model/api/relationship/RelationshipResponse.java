package com.sericulture.masterdata.model.api.relationship;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RelationshipResponse {

    @Schema(name="relationshipId", example = "1")
    int relationshipId;

    @Schema(name = "relationshipName", example = "Father")
    String relationshipName;

}
