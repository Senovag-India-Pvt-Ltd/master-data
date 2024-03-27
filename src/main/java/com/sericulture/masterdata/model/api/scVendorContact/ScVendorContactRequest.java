package com.sericulture.masterdata.model.api.scVendorContact;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScVendorContactRequest extends RequestBody {


    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Vendor Address contain only letters and numbers")
    @Schema(name = "vendorAddress", example = "Karnataka", required = true)
    String vendorAddress;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Phone must contain only letters and numbers")
    @Schema(name = "phone", example = "Karnataka", required = true)
    String phone;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Email must contain only letters and numbers")
    @Schema(name = "email", example = "Karnataka", required = true)
    String email;

    @Schema(name = "scVendorId", example = "1")
    Long scVendorId;


}
