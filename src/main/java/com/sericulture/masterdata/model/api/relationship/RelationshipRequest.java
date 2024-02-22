package com.sericulture.masterdata.model.api.relationship;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RelationshipRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Relationship must contain only letters and numbers")
    @Schema(name = "relationshipName", example = "Father", required = true)
    String relationshipName;


    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Relationship in kannada must contain only letters and numbers")
    @Schema(name = "relationshipNameInKannada", example = "ಭಾಷೆ")
    String relationshipNameInKannada;
}
