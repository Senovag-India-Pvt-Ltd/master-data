package com.sericulture.masterdata.model.api.generationNumberMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class GenerationNumberMasterRequest extends RequestBody {
//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "GenerationNumber must contain only letters and numbers")
    @Schema(name = "generationNumber", example = "Karnataka", required = true)
    String generationNumber;
}
