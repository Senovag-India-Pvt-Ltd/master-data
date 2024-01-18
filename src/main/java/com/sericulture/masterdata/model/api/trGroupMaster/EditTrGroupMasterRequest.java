package com.sericulture.masterdata.model.api.trGroupMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class EditTrGroupMasterRequest extends RequestBody {
    @Schema(name = "trGroupMasterId", example = "1")
    Integer trGroupMasterId;

    @Schema(name = "trGroupMasterName", example = "Karnataka", required = true)
    String trGroupMasterName;
}
