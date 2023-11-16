package com.sericulture.masterdata.model.api.vendorMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditVendorMasterRequest extends RequestBody {

    @Schema(name = "vendorMasterId", example = "1")
    Integer vendorMasterId;

    @Schema(name = "vendorMasterName", example = "vendor 1", required = true)
    String vendorMasterName;
}
