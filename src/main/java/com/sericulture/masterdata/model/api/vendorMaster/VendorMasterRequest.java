package com.sericulture.masterdata.model.api.vendorMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VendorMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Vendor name must contain only letters and numbers")
    @Schema(name = "vendorMasterName", example = "vendor 1", required = true)
    String vendorMasterName;
}
