package com.sericulture.masterdata.model.api.workingInstitution;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WorkingInstitutionRequest extends RequestBody {

    @Schema(name = "workingInstitutionName", example = "Karnataka", required = true)
    String workingInstitutionName;

    @Schema(name = "workingInstitutionNameInKannada",  example = "ಭಾಷೆ")
    String workingInstitutionNameInKannada;
}
