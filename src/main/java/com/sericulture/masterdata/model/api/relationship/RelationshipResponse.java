package com.sericulture.masterdata.model.api.relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipResponse {

    @Schema(name="relationshipId", example = "1")
    int relationshipId;

    @Schema(name = "relationshipName", example = "Father")
    String relationshipName;

}
