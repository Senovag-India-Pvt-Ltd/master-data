package com.sericulture.masterdata.model.api.trInstitutionMaster;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class EditTrInstitutionMasterRequest extends RequestBody {
    @Schema(name = "trInstitutionMasterId", example = "1")
    Integer trInstitutionMasterId;

    @Schema(name = "trInstitutionMasterName", example = "Karnataka", required = true)
    String trInstitutionMasterName;

}
