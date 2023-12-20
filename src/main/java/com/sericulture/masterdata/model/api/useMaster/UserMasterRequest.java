package com.sericulture.masterdata.model.api.useMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserMasterRequest extends RequestBody {

    @Schema(name = "firstName", example = "Shraddha", required = true)
    String firstName;

    @Schema(name = "middleName", example = "Nagaraja", required = true)
    String middleName;

    @Schema(name = "lastName", example = "Kharvi", required = true)
    String lastName;

    @Schema(name = "password", example = "12345", required = true)
    String password;

    @Schema(name = "emailID", example = "12S", required = true)
    String emailID;

    @Schema(name = "stateId", example = "1", required = true)
    Long stateId;

    @Schema(name = "districtId", example = "1", required = true)
    Long districtId;

    @Schema(name = "talukId", example = "1", required = true)
    Long talukId;

    @Schema(name = "roleId", example = "1", required = true)
    Long roleId;

    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;

    @Schema(name = "username", example = "test")
    String username;

    @Schema(name = "designationId", example = "1")
    Long designationId;

    @Schema(name = "phoneNumber", example = "9988776655")
    String phoneNumber;

    @Schema(name = "userType", example = "1")
    int userType;

    @Schema(name = "userTypeId", example = "1")
    Long userTypeId;

    @Schema(name = "deviceId", example = "998s3gf6478776655")
    String deviceId;
}
