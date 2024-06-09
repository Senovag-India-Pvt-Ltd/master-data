package com.sericulture.masterdata.model.api.generationNumberMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditGenerationNumberMasterRequest extends RequestBody {

    @Schema(name = "generationNumberId", example = "1")
    Long generationNumberId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "generationNumber  must contain only letters and numbers")
    @Schema(name = "generationNumber", example = "Karnataka", required = true)
    String generationNumber;
}
