package com.sericulture.masterdata.model.api.releerTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditReleerTypeMasterRequest extends RequestBody {
    @Schema(name = "releerTypeMasterId", example = "1")
    Long releerTypeMasterId;

    @Schema(name = "releerTypeMasterName", example = "Karnataka", required = true)
    String releerTypeMasterName;

    @Schema(name = "noOfDeviceAllowed", example = "3")
    private Long noOfDeviceAllowed;
}
