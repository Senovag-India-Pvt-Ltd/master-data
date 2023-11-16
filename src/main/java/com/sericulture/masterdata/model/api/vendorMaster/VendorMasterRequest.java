package com.sericulture.masterdata.model.api.vendorMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VendorMasterRequest extends RequestBody {

    @Schema(name = "vendorMasterName", example = "vendor 1", required = true)
    String vendorMasterName;
}
