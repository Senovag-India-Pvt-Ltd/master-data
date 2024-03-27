package com.sericulture.masterdata.model.api.scVendorContact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScVendorContactResponse {

    @Schema(name = "scVendorContactId", example = "1")
    Long scVendorContactId;


    @Schema(name = "vendorAddress", example = "Karnataka", required = true)
    String vendorAddress;

    @Schema(name = "phone", example = "Karnataka", required = true)
    String phone;

    @Schema(name = "email", example = "Karnataka", required = true)
    String email;

    @Schema(name = "scVendorId", example = "1")
    Long scVendorId;

    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;


}
