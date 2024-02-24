package com.sericulture.masterdata.model.api.useMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SaveReelerUserRequest extends RequestBody {
    @Schema(name = "reelerId", example = "1", required = true)
    Long reelerId;

    @Pattern(regexp = "^[a-zA-Z0-9._@]*$", message = "User name must contain only letters and numbers")
    @Schema(name = "username", example = "test")
    String username;

    @Schema(name = "password", example = "12345", required = true)
    String password;

    @Pattern(regexp = "^[a-zA-Z0-9@.\\s]*$", message = "Email must contain only letters and numbers")
    @Schema(name = "emailID", example = "12S", required = true)
    String emailID;

    @Schema(name = "roleId", example = "1", required = true)
    Long roleId;

    @Pattern(regexp = "^[+0-9\\s]*$", message = "Phone number must contain only letters and numbers")
    @Schema(name = "phoneNumber", example = "9988776655")
    String phoneNumber;

    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;

    @Schema(name = "designationId", example = "1")
    Long designationId;

    @Pattern(regexp = "^[a-zA-Z0-9-\\s]*$", message = "Device id must contain only letters and numbers")
    @Schema(name = "deviceId", example = "998s3gf6478776655")
    String deviceId;

    @Schema(name = "walletAmount", example = "1200.00")
    Double walletAMount;

    @Schema(name = "externalUnitRegistrationId", example = "1", required = true)
    Long externalUnitRegistrationId;
}