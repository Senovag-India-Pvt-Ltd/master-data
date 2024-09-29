package com.sericulture.masterdata.model.api.diseaseStatus;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DiseaseStatusRequest extends RequestBody {


    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Name must contain only letters and numbers")
    @Schema(name = "name",  example = "Karnataka",required=true)
    String name;

    @Schema(name = "description",  example = "Karnataka",required=true)
    String description;
}
