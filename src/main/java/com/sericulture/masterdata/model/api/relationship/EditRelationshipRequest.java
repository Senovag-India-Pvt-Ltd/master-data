package com.sericulture.masterdata.model.api.relationship;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditRelationshipRequest extends RequestBody {
    @Schema(name = "relationshipId", example = "1")
    Integer relationshipId;

    @Schema(name = "relationshipName", example = "Father", required = true)
    String relationshipName;
}
