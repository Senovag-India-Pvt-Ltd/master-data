package com.sericulture.masterdata.model.api.trInstitutionMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TrInstitutionMasterRequest extends RequestBody {
    @Schema(name = "trInstitutionMasterName", example = "Karnataka", required = true)
    String trInstitutionMasterName;

    @Schema(name = "trInstitutionNameInKannada", example = "Karnataka", required = true)
    String trInstitutionNameInKannada;
}
