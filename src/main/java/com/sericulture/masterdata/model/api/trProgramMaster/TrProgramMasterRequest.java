package com.sericulture.masterdata.model.api.trProgramMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TrProgramMasterRequest extends RequestBody {

    @Schema(name = "trProgramMasterName", example = "Karnataka", required = true)
    String trProgramMasterName;

    @Schema(name = "trProgramNameInKannada", example = "ಕನ್ನಡ")
    String trProgramNameInKannada;
}
