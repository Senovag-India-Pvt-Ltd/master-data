package com.sericulture.masterdata.model.api.reelerTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditReelerTypeMasterRequest extends RequestBody {
    @Schema(name = "reelerTypeMasterId", example = "1")
    Long reelerTypeMasterId;

    @Schema(name = "reelerTypeMasterName", example = "Karnataka", required = true)
    String reelerTypeMasterName;

    @Schema(name = "noOfDeviceAllowed", example = "3")
    private Long noOfDeviceAllowed;
}
