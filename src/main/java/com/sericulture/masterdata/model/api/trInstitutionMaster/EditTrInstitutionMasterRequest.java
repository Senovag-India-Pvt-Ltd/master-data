package com.sericulture.masterdata.model.api.trInstitutionMaster;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class EditTrInstitutionMasterRequest extends RequestBody {
    @Schema(name = "trInstitutionMasterId", example = "1")
    Integer trInstitutionMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr Institution name must contain only letters and numbers")
    @Schema(name = "trInstitutionMasterName", example = "Karnataka", required = true)
    String trInstitutionMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Tr Institution name in kannada must contain only letters and numbers")
    @Schema(name = "trInstitutionNameInKannada", example = "ಕನ್ನಡ")
    String trInstitutionNameInKannada;

}
