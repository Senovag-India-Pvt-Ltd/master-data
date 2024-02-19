package com.sericulture.masterdata.model.api.disinfectantMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditDisinfectantMasterRequest extends RequestBody {

    @Schema(name = "disinfectantMasterId", example = "1")
    Long disinfectantMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Disinfectant name must contain only letters and numbers")
    @Schema(name = "disinfectantMasterName", example = "Karnataka", required = true)
    String disinfectantMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Disinfectant in kannada must contain only letters and numbers")
    @Schema(name = "disinfectantMasterNameInKannada", example = "ಭಾಷೆ", required = true)
    String disinfectantMasterNameInKannada;

}
