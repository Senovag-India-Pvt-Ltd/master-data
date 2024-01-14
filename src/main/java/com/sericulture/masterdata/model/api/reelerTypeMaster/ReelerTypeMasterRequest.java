package com.sericulture.masterdata.model.api.reelerTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReelerTypeMasterRequest extends RequestBody {

    @Schema(name = "reelerTypeMasterName", example = "Karnataka", required = true)
    String reelerTypeMasterName;

    @Schema(name = "noOfDeviceAllowed", example = "3")
    private Long noOfDeviceAllowed;
}
