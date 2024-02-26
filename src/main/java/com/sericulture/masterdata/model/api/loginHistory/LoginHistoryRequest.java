package com.sericulture.masterdata.model.api.loginHistory;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginHistoryRequest extends RequestBody {
    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "UserName must contain only letters and numbers")
    @Schema(name = "username", example = "Karnataka",required=true)
    String username;

    @Schema(name = "loginTime", example = "1")
    Date loginTime;

    @Schema(name = "logoutTime", example = "1")
    Date logoutTime;

    @Schema(name = "loginStatus", example = "1")
    Long loginStatus;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "IpAddress must contain only letters and numbers")
    @Schema(name = "ipAddress", example = "Karnataka",required=true)
    String ipAddress;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Device Info must contain only letters and numbers")
    @Schema(name = "deviceInfo", example = "Karnataka",required=true)
    String deviceInfo;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Error description must contain only letters and numbers")
    @Schema(name = "errorDescription", example = "Karnataka",required=true)
    String errorDescription;

}
