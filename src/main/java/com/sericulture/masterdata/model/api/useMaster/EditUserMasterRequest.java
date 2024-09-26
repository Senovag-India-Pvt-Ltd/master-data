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
public class EditUserMasterRequest extends RequestBody {

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;

//    @Pattern(regexp = "^[a-zA-Z0-9._@ ]*$", message = "First name must contain only letters and numbers")
    @Schema(name = "firstName", example = "Shraddha", required = true)
    String firstName;

//    @Pattern(regexp = "^[a-zA-Z0-9._@ ]*$", message = "Middle name must contain only letters and numbers")
    @Schema(name = "middleName", example = "Nagaraja", required = true)
    String middleName;

//    @Pattern(regexp = "^[a-zA-Z0-9._@ ]*$", message = "Last name must contain only letters and numbers")
    @Schema(name = "lastName", example = "Kharvi", required = true)
    String lastName;

//    @Schema(name = "password", example = "12345", required = true)
    String password;

//    @Pattern(regexp = "^[a-zA-Z0-9@.\\s]*$", message = "Email must contain only letters and numbers")
    @Schema(name = "emailID", example = "12S", required = true)
    String emailID;

    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Long districtId;

    @Schema(name = "talukId", example = "1")
    Long talukId;

    @Schema(name = "roleId", example = "1")
    Long roleId;

    @Schema(name = "marketMasterId", example = "1")
    Long marketMasterId;

//    @Pattern(regexp = "^[a-zA-Z0-9._@]*$", message = "User name must contain only letters and numbers")
    @Schema(name = "username", example = "test")
    String username;

    @Schema(name = "designationId", example = "1")
    Long designationId;

//    @Pattern(regexp = "^[+0-9\\s]*$", message = "Phone number must contain only numbers")
    @Schema(name = "phoneNumber", example = "9988776655")
    String phoneNumber;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "ddoCode name must contain only letters and numbers")
    @Schema(name = "ddoCode", example = "ddoCode 1", required = true)
    String ddoCode;

    @Schema(name = "userType", example = "1")
    int userType;

    @Schema(name = "userTypeId", example = "1")
    Long userTypeId;

//    @Pattern(regexp = "^[a-zA-Z0-9-\\s]*$", message = "Device Id must contain only letters and numbers")
    @Schema(name = "deviceId", example = "998s3gf6478776655")
    String deviceId;

    @Schema(name = "workingInstitutionId", example = "1")
    Long workingInstitutionId;

    @Schema(name = "tscMasterId", example = "1")
    Long tscMasterId;
}
