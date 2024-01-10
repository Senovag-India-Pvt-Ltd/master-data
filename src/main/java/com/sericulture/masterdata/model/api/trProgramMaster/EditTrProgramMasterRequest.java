package com.sericulture.masterdata.model.api.trProgramMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditTrProgramMasterRequest extends RequestBody {

    @Schema(name = "trProgramMasterId", example = "1")
    Integer trProgramMasterId;

    @Schema(name = "trProgramMasterName", example = "Karnataka", required = true)
    String trProgramMasterName;
}
